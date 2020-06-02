package com.suusoft.elistening.DaoDownload;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suusoft.elistening.model.modelLesson.Lesson;

@Entity(tableName="download")
public class DownloadList implements Parcelable {


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

    public DownloadList() {

    }

    protected DownloadList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        attachment = in.readString();
        image = in.readString();
        overview = in.readString();
        type = in.readString();
        content = in.readString();
        isActive = in.readInt();
        isHot = in.readInt();
        isTop = in.readInt();
        rate = in.readInt();
        rateCount = in.readInt();
        viewCount = in.readInt();
        subtitle = in.readString();
        level = in.readString();
        categoryId = in.readString();
    }

    public static final Creator<DownloadList> CREATOR = new Creator<DownloadList>() {
        @Override
        public DownloadList createFromParcel(Parcel in) {
            return new DownloadList(in);
        }

        @Override
        public DownloadList[] newArray(int size) {
            return new DownloadList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(attachment);
        dest.writeString(image);
        dest.writeString(overview);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeInt(isActive);
        dest.writeInt(isHot);
        dest.writeInt(isTop);
        dest.writeInt(rate);
        dest.writeInt(rateCount);
        dest.writeInt(viewCount);
        dest.writeString(image);
        dest.writeString(level);
        dest.writeString(categoryId);
    }
}
