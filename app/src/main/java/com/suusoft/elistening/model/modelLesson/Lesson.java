package com.suusoft.elistening.model.modelLesson;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suusoft on 10/16/2017.
 */
@Entity(tableName="favorite")
public class Lesson implements Parcelable{
//    private int id;
//    private String title;
//    private String urlImage;
//    private String overview;
//    private String urlAudio;
//    private String link_url;
//    private String type;
//    private ArrayList<Content> contents;
//    private ArrayList<Vocabulary> vocabularys;
//
//    private int isActive, isHot, isTop, rate, rateCount, viewCount;
//    private String subtitle;
//    private String level, content ,category_id;
//


    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("code")
    @Expose
    @ColumnInfo(name = "code")
    private String code;
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    private String image;
    @SerializedName("overview")
    @Expose
    @ColumnInfo(name = "overview")
    private String overview;
    @SerializedName("content")
    @Expose
    @ColumnInfo(name = "content")
    private String content;
    @SerializedName("attachment")
    @Expose
    @ColumnInfo(name = "attachment")
    private String attachment;
    @SerializedName("subtitle")
    @Expose
    @ColumnInfo(name = "subtitle")
    private String subtitle;
    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    private String author;
    @SerializedName("link_url")
    @Expose
    @ColumnInfo(name = "linkUrl")
    private String linkUrl;
    @SerializedName("is_active")
    @Expose
    @ColumnInfo(name = "is_active")
    private Integer isActive;
    @SerializedName("sort_order")
    @Expose
    @ColumnInfo(name = "sort_order")
    private String sortOrder;
    @SerializedName("is_hot")
    @Expose
    @ColumnInfo(name = "is_hot")
    private Integer isHot;
    @SerializedName("is_top")
    @Expose
    @ColumnInfo(name = "is_top")
    private Integer isTop;
    @SerializedName("rate")
    @Expose
    @ColumnInfo(name = "rate")
    private Integer rate;
    @SerializedName("rate_count")
    @Expose
    @ColumnInfo(name = "rate_count")
    private Integer rateCount;
    @SerializedName("view_count")
    @Expose
    @ColumnInfo(name = "view_count")
    private Integer viewCount;
    @SerializedName("level")
    @Expose
    @ColumnInfo(name = "level")
    private String level;
    @SerializedName("type")
    @Expose
    @ColumnInfo(name = "type")
    private String type;
    @SerializedName("category_id")
    @Expose
    @ColumnInfo(name = "categoryId")
    private String categoryId;
    @SerializedName("created_date")
    @Expose
    @ColumnInfo(name = "created_date")
    private String createdDate;
    @SerializedName("created_user")
    @Expose
    @ColumnInfo(name = "created_user")
    private String createdUser;
    @SerializedName("modified_date")
    @Expose
    @ColumnInfo(name = "modified_date")
    private String modifiedDate;
    @SerializedName("modified_user")
    @Expose
    @ColumnInfo(name = "modified_user")
    private String modifiedUser;
    @SerializedName("application_id")
    @Expose
    @ColumnInfo(name = "application_id")
    private String applicationId;

    private boolean isDownload;
    private boolean isFavorite;
    private boolean isView;



    public Lesson() {
    }

    public Lesson(String name, String image, String overview) {
        this.name = name;
        this.image = image;
        this.overview = overview;
    }


    protected Lesson(Parcel in) {
//        int hasId = in.readInt();
//
//        if (hasId == 1) {
//            id = in.readInt();
//        }
//        else {
//            id = null;
//        }
        id = in.readInt();
        name = in.readString();
        attachment = in.readString();
        image = in.readString();
        overview = in.readString();
//        urlAudio = in.readString();
        type = in.readString();
//        content = in.createTypedArrayList(Content.CREATOR);
        content = in.readString();
//        vocabularys = in.createTypedArrayList(Vocabulary.CREATOR);
        isActive = in.readInt();
        isHot = in.readInt();
        isTop = in.readInt();
        rate = in.readInt();
        rateCount = in.readInt();
        viewCount = in.readInt();
        subtitle = in.readString();
        level = in.readString();
//        content = in.readString();
        categoryId = in.readString();
        isDownload = in.readByte() != 0;
        isFavorite = in.readByte() != 0;
        isView = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        if (id == null) {
//            dest.writeInt(0);
//        }else {
//            dest.writeInt(1);
//            dest.writeInt(id);
//        }
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(attachment);
        dest.writeString(image);
        dest.writeString(overview);
       // dest.writeString(urlAudio);
        dest.writeString(type);
        dest.writeString(content);
//        dest.writeTypedList(vocabularys);
        dest.writeInt(isActive);
        dest.writeInt(isHot);
        dest.writeInt(isTop);
        dest.writeInt(rate);
        dest.writeInt(rateCount);
        dest.writeInt(viewCount);
        dest.writeString(image);
        dest.writeString(level);
//        dest.writeString(content);
        dest.writeString(categoryId);
        dest.writeByte((byte) (isDownload ? 1 : 0));
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeByte((byte) (isView ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

//    public int getIdTranscript(int time) {
//        int size = content.size() - 1;
//        if (time > 0 && time <= content.get(size).getTime()) {
//            for (int i = 0; i < content.size() - 1; i++) {
//                if (time == content.get(i).getTime()) return i;
//                else if (time > content.get(i).getTime() && time < content.get(i + 1).getTime())
//                    return i;
//            }
//        } else if (time > content.get(size).getTime()) return size;
//        return -1;
//    }

//    public int getIdTranscriptSoftByTime(int time) {
//        ArrayList<Content> transcriptsSoftByTime = ParserUtility.sortedByTime(content);
//        if (transcriptsSoftByTime.size()>0){
//            int size = transcriptsSoftByTime.size() - 1;
//            int pos = -1;
//            if (time > 0 && time <= transcriptsSoftByTime.get(size).getTime()) {
//                for (int i = 0; i < size; i++) {
//                    pos = i;
//                    if ((time == transcriptsSoftByTime.get(pos).getTime() && time == transcriptsSoftByTime.get(pos + 1).getTime())
//                            || (time >= transcriptsSoftByTime.get(pos).getTime() && time < transcriptsSoftByTime.get(pos + 1).getTime()) )
//                        return pos;
//                }
//                Log.e("pos", "pos " + pos);
//            } else if (time > transcriptsSoftByTime.get(size).getTime()){
//                return size;
//            }
//        }else return 0;
//
//        return 0;
//    }

//    public String getLink_url() {
//        return link_url;
//    }
//
//    public void setLink_url(String link_url) {
//        this.link_url = link_url;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getUrlImage() {
//        return urlImage;
//    }
//
//    public void setUrlImage(String urlImage) {
//        this.urlImage = urlImage;
//    }
//
//    public String getOverview() {
//        return overview;
//    }
//
//    public void setOverview(String overview) {
//        this.overview = overview;
//    }
//
//    public String getUrlAudio() {
//        return urlAudio;
//    }
//
//    public void setUrlAudio(String urlAudio) {
//        this.urlAudio = urlAudio;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public ArrayList<Content> getArrTranscript() {
//        return contents;
//    }
//
//    public void setContents(ArrayList<Content> contents) {
//        this.contents = contents;
//    }
//
//    public ArrayList<Vocabulary> getVocabularys() {
//        return vocabularys;
//    }
//
//    public void setVocabularys(ArrayList<Vocabulary> vocabularys) {
//        this.vocabularys = vocabularys;
//    }
//
//    public int getIsActive() {
//        return isActive;
//    }
//
//    public void setIsActive(int isActive) {
//        this.isActive = isActive;
//    }
//
//    public int getIsHot() {
//        return isHot;
//    }
//
//    public void setIsHot(int isHot) {
//        this.isHot = isHot;
//    }
//
//    public int getIsTop() {
//        return isTop;
//    }
//
//    public void setIsTop(int isTop) {
//        this.isTop = isTop;
//    }
//
//    public int getRate() {
//        return rate;
//    }
//
//    public void setRate(int rate) {
//        this.rate = rate;
//    }
//
//    public int getRateCount() {
//        return rateCount;
//    }
//
//    public void setRateCount(int rateCount) {
//        this.rateCount = rateCount;
//    }
//
//    public int getViewCount() {
//        return viewCount;
//    }
//
//    public void setViewCount(int viewCount) {
//        this.viewCount = viewCount;
//    }
//
//    public String getSubtitle() {
//        return subtitle;
//    }
//
//    public void setSubtitle(String subtitle) {
//        this.subtitle = subtitle;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getCategory_id() {
//        return category_id;
//    }
//
//    public void setCategory_id(String category_id) {
//        this.category_id = category_id;
//    }
//
//    public boolean isDownload() {
//        return isDownload;
//    }
//
//    public void setDownload(boolean download) {
//        isDownload = download;
//    }
//
//    public boolean isFavorite() {
//        return isFavorite;
//    }
//
//    public void setFavorite(boolean favorite) {
//        isFavorite = favorite;
//    }
//
//    public boolean isView() {
//        return isView;
//    }
//
//    public void setView(boolean view) {
//        isView = view;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public static Creator<Lesson> getCREATOR() {
        return CREATOR;
    }
    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }
}
