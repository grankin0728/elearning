package com.suusoft.elistening.util;

import android.annotation.SuppressLint;

public class UnsignUtils {

    /** Mang chu cai tieng viet A */
    private static final char[] charA = { 'à', 'á', 'ạ', 'ả', 'ã',// 0->16
            'â', 'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ' }; // a,

    /** Mang chu cai tieng viet E */
    private static final char[] charE = { 'ê', 'ề', 'ế', 'ệ', 'ể', 'ễ',// 17->27
            'è', 'é', 'ẹ', 'ẻ', 'ẽ' }; // e

    /** Mang chu cai tieng viet I */
    private static final char[] charI = { 'ì', 'í', 'ị', 'ỉ', 'ĩ' }; // i
    // 28->32
    /** Mang chu cai tieng viet O */
    private static final char[] charO = { 'ò', 'ó', 'ọ', 'ỏ', 'õ',// o
            // 33->49
            'ô', 'ồ', 'ố', 'ộ', 'ổ', 'ỗ',// ô
            'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ' }; // ơ

    /** Mang chu cai tieng viet U */
    private static final char[] charU = { 'ù', 'ú', 'ụ', 'ủ', 'ũ',// u
            // 50->60
            'ư', 'ừ', 'ứ', 'ự', 'ử', 'ữ' }; // ư

    /** Mang chu cai tieng viet Y */
    private static final char[] charY = { 'ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ' }; // y
    // 61->65
    /** Mang chu cai tieng viet D */
    private static final char[] charD = { 'đ', ' ' }; // D 66-67

    /** Mang 2 chieu chu cai tieng viet */
    private static final char[][] CH = { charA, charE, charI, charO, charU,
            charY, charD };

    /** chuoi cac ky tu chu cai tieng viet */
    private static String charact;

    static {
        charact = String.valueOf(charA, 0, charA.length)
                + String.valueOf(charE, 0, charE.length)
                + String.valueOf(charI, 0, charI.length)
                + String.valueOf(charO, 0, charO.length)
                + String.valueOf(charU, 0, charU.length)
                + String.valueOf(charY, 0, charY.length)
                + String.valueOf(charD, 0, charD.length);
    }

    /**
     * lay ky tu khong dau tu tieng viet.
     *
     * @param pC
     *            ky tu tieng viet
     * @return ky tu khong dau
     */
    private static char GetAlterChar(char pC) {
        if ((int) pC == 32) {
            return ' ';
        }

        char tam = pC;// Character.toLowerCase(pC);

        int i = 0;
        while (i < charact.length() && charact.charAt(i) != tam) {
            i++;
        }
        if (i < 0 || i > 67)
            return pC;

        if (i == 66) {
            return 'd';
        }
        if (i >= 0 && i <= 16) {
            return 'a';
        }
        if (i >= 17 && i <= 27) {
            return 'e';
        }
        if (i >= 28 && i <= 32) {
            return 'i';
        }
        if (i >= 33 && i <= 49) {
            return 'o';
        }
        if (i >= 50 && i <= 60) {
            return 'u';
        }
        if (i >= 61 && i <= 65) {
            return 'y';
        }
        return pC;
    }

    /**
     * chuyen doi chu co dau ve khong dau
     *
     * @param pStr
     *            chuoi
     * @return chuoi khong dau
     */
    @SuppressLint("DefaultLocale")
    public static String getUnsign(String pStr) {
        char[] convertString = pStr.toLowerCase().toCharArray();
        for (int i = 0; i < convertString.length; i++) {
            char temp = convertString[i];
            if ((int) temp < 97 || temp > 122) {
                char tam1 = GetAlterChar(temp);
                if ((int) temp != 32)
                    convertString[i] = tam1;
            }
        }
        return String.valueOf(convertString);
    }

}
