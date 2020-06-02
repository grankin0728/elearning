package com.suusoft.elistening.network;

import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suusoft
 * <p/>
 * API Helper class.
 */
public class ApiManager {
    public static final String TAG = ApiManager.class.getSimpleName();
    public static final int METHOD_POST = com.android.volley.Request.Method.POST;
    public static final int METHOD_GET = com.android.volley.Request.Method.GET;
    public static final int METHOD_PUT = com.android.volley.Request.Method.PUT;
    public static final int METHOD_DELETE = com.android.volley.Request.Method.DELETE;

    private HashMap<String, String> defaultParams;

    private static ApiManager mInstance;

    public static ApiManager getInstance() {
        if (mInstance == null)
            mInstance = new ApiManager();

        return mInstance;
    }

    private ApiManager() {
        defaultParams = new HashMap<String, String>();
    }

    public void setDefaultParams(HashMap<String, String> defaultParams) {
        this.defaultParams = defaultParams;
    }

    public HashMap<String, String> getDefaultParams() {
        return defaultParams;
    }

    // public static String getSessionId() {
    // // do stuff here
    // DataStore dataStore = DataStore.getInstance();
    // if (dataStore == null) {
    // Log.e("--------------> data store null");
    // }
    // String result = dataStore.getString(Constant.KEY_SESSION_ID);
    // return result;
    // }

    public static void get(String url, final CompleteListener listener) {
        ApiManager.getInstance().request(METHOD_GET, url, null, listener, null);
    }

    public static void get(String url, HashMap<String, String> params, final CompleteListener listener, String tag) {
        ApiManager.getInstance().request(METHOD_GET, url, params, listener, tag);
    }

    public static void get(String url, HashMap<String, String> params, final CompleteListener listener) {
        ApiManager.getInstance().request(METHOD_GET, url, params, listener, null);
    }

    public static void post(String url, final CompleteListener listener) {
        ApiManager.getInstance().request(METHOD_POST, url, null, listener, null);
    }

    public static void post(String url, HashMap<String, String> params, final CompleteListener listener) {
        ApiManager.getInstance().request(METHOD_POST, url, params, listener, null);
    }

    public static void post(String url, HashMap<String, String> params, final CompleteListener listener, String tag) {
        ApiManager.getInstance().request(METHOD_POST, url, params, listener, tag);
    }

    public void request(final int method, String url, HashMap<String, String> params, final CompleteListener listener,
                        String tag) {
        // adding default params
        HashMap<String, String> fullParams = new HashMap<String, String>();
//        fullParams.putAll(this.defaultParams);
        if (params != null) {
            fullParams.putAll(params);
        }

        Log.d(TAG, "request params:" + fullParams.toString());
        if (method == METHOD_GET) {
            Uri.Builder b = Uri.parse(url).buildUpon();
            for (Map.Entry<String, String> entry : fullParams.entrySet()) {
                b.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            url = b.toString();
            fullParams.clear();
        }
        Log.e(TAG, "url: " + url);

        Listener<JSONObject> completeListener = new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "RESULT: " + response);
                if (listener != null) {
                    if (listener instanceof CompleteListener) {
                        ApiResponse apiResponse = new ApiResponse(response);
                        if (!apiResponse.isError()) {
                            listener.onSuccess(apiResponse);
                        } else {
                            listener.onError(apiResponse.getMessage());
                        }
                    }

                }
            }
        };
        ErrorListener errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "volley error: " + error.getClass());
                if (listener != null) {
                    String errorMessage = "";
                    int code = ApiResponse.ERROR_CODE_UNKOWN;
                    if (error.getClass() == TimeoutError.class) {
                        code = ApiResponse.ERROR_CODE_REQUEST_TIMEOUT;
                        errorMessage = "Request timeout";
                    } else if (error.getClass() == AuthFailureError.class) {
                        errorMessage = error.getMessage() != null ? error.getMessage() : "No internet permission ?";
                    } else {
                        errorMessage = error.getMessage();
                        if (errorMessage == null || errorMessage.length() == 0)
                            errorMessage = error.toString();
                        code = error.networkResponse != null ? error.networkResponse.statusCode
                                : ApiResponse.ERROR_CODE_UNKOWN;
                    }

                    // if (code == Constant.API_ERROR_CODE_UNAUTHORIZED) {
                    // // show login popup
                    // } else if (code ==
                    // Constant.API_ERROR_CODE_SERVER_OFFLINE) {
                    // // show alert and turn off the app-
                    // }

                    if (listener instanceof CompleteListener)
                        listener.onError(new ApiResponse(code, errorMessage).getMessage());

                }
            }
        };

        Log.d(TAG, "starting request url:" + url);
        ApiHelperJsonObjectRequest jr = new ApiHelperJsonObjectRequest(method, url, fullParams, completeListener,
                errorListener);
        if (tag != null) {
            jr.setTag(tag);
        }
        jr.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jr.setShouldCache(false);
        VolleyRequestManager.getRequestQueue().add(jr);
    }

    public interface CompleteListener {
        public void onSuccess(ApiResponse response);

        public void onError(String message);

    }

    /**
     * A request for retrieving a {@link JSONObject} response body at a given
     * URL. Use for posting params along with request, instead of posting body
     * like {@link JsonObjectRequest}
     */
    private class ApiHelperJsonObjectRequest extends JsonObjectRequest {
        Map<String, String> mParams;

        public ApiHelperJsonObjectRequest(int method, String url, Map<String, String> params,
                                          Listener<JSONObject> listener, ErrorListener errorListener) {
            super(method, url, null, listener, errorListener);
            mParams = params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<String, String>();
            params.putAll(super.getHeaders());
            //params.put("apikey", new String(Constant.API_KEY));

            Log.d(TAG, "header params:" + params.toString());
            return params;
        }

        // override getBodyContentType and getBody for prevent posting body.
        @Override
        public String getBodyContentType() {
            return null;
        }

        @Override
        public byte[] getBody() {
            if (this.getMethod() == METHOD_POST && mParams != null && mParams.size() > 0) {
                return encodeParameters(mParams, getParamsEncoding());
            }
            return null;
        }

        /**
         * Converts <code>params</code> into an
         * application/x-www-form-urlencoded encoded string.
         */
        private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
//					encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
//					encodedParams.append('=');
//					encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
//					encodedParams.append('&');

                    encodedParams.append(entry.getKey());
                    encodedParams.append('=');
                    encodedParams.append(entry.getValue());
                    encodedParams.append('&');
                }
                return encodedParams.toString().getBytes(paramsEncoding);
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }

        }

    }

}
