package com.student.info.system.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 学生信息实体类,实现Parcelable接口目的，使其能在内存中以对象形式传递
 */
public class StudentInfoEntities implements Parcelable {
    /**
     * 学号
     */
    private String stuIdLabel;
    /**
     * 姓名
     */
    private String nameLabel;
    /**
     * 身份证号
     */
    private String idNumberLabel;
    /**
     * 性别
     */
    private String sexLabel;
    /**
     * 籍贯
     */
    private String nativePlaceLabel;
    /**
     * 生日
     */
    private String birthdayLabel;
    /**
     * 所在学院
     */
    private String school;
    /**
     * 专业
     */
    private String majorLabel;
    /**
     * 班级
     */
    private String stuClassLabel;
    /**
     * 电话
     */
    private String phoneLabel;
    /**
     * 电子邮箱
     */
    private String emailLabel;
    /**
     * 微信
     */
    private String weixinLabel;
    /**
     * 特长
     */
    private String specialSkillLabel;

    public StudentInfoEntities() {
    }

    private StudentInfoEntities(Parcel in) {
        stuIdLabel = in.readString();
        nameLabel = in.readString();
        idNumberLabel = in.readString();
        sexLabel = in.readString();
        nativePlaceLabel = in.readString();
        birthdayLabel = in.readString();
        school = in.readString();
        majorLabel = in.readString();
        stuClassLabel = in.readString();
        phoneLabel = in.readString();
        emailLabel = in.readString();
        weixinLabel = in.readString();
        specialSkillLabel = in.readString();
    }


    public String getStuIdLabel() {
        return stuIdLabel;
    }

    public void setStuIdLabel(String stuIdLabel) {
        this.stuIdLabel = stuIdLabel;
    }

    public String getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
    }

    public String getIdNumberLabel() {
        return idNumberLabel;
    }

    public void setIdNumberLabel(String idNumberLabel) {
        this.idNumberLabel = idNumberLabel;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }

    public String getNativePlaceLabel() {
        return nativePlaceLabel;
    }

    public void setNativePlaceLabel(String nativePlaceLabel) {
        this.nativePlaceLabel = nativePlaceLabel;
    }

    public String getBirthdayLabel() {
        return birthdayLabel;
    }

    public void setBirthdayLabel(String birthdayLabel) {
        this.birthdayLabel = birthdayLabel;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajorLabel() {
        return majorLabel;
    }

    public void setMajorLabel(String majorLabel) {
        this.majorLabel = majorLabel;
    }

    public String getStuClassLabel() {
        return stuClassLabel;
    }

    public void setStuClassLabel(String stuClassLabel) {
        this.stuClassLabel = stuClassLabel;
    }

    public String getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(String phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public String getEmailLabel() {
        return emailLabel;
    }

    public void setEmailLabel(String emailLabel) {
        this.emailLabel = emailLabel;
    }

    public String getWeixinLabel() {
        return weixinLabel;
    }

    public void setWeixinLabel(String weixinLabel) {
        this.weixinLabel = weixinLabel;
    }

    public String getSpecialSkillLabel() {
        return specialSkillLabel;
    }

    public void setSpecialSkillLabel(String specialSkillLabel) {
        this.specialSkillLabel = specialSkillLabel;
    }

    public static final Creator<StudentInfoEntities> CREATOR = new Creator<StudentInfoEntities>() {
        @Override
        public StudentInfoEntities createFromParcel(Parcel in) {
            return new StudentInfoEntities(in);
        }

        @Override
        public StudentInfoEntities[] newArray(int size) {
            return new StudentInfoEntities[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stuIdLabel);
        dest.writeString(nameLabel);
        dest.writeString(idNumberLabel);
        dest.writeString(sexLabel);
        dest.writeString(nativePlaceLabel);
        dest.writeString(birthdayLabel);
        dest.writeString(school);
        dest.writeString(majorLabel);
        dest.writeString(stuClassLabel);
        dest.writeString(phoneLabel);
        dest.writeString(emailLabel);
        dest.writeString(weixinLabel);
        dest.writeString(specialSkillLabel);
    }

    @Override
    public String toString() {
        return "姓名：" + nameLabel + '\n' +
                "学号：" + stuIdLabel + '\n' +
                "身份证号：" + idNumberLabel + '\n' +
                "性别：" + sexLabel + '\n' +
                "籍贯：" + nativePlaceLabel + '\n' +
                "生日：" + birthdayLabel + '\n' +
                "所在院校：" + school + '\n' +
                "专业：" + majorLabel + '\n' +
                "班级：" + stuClassLabel + '\n' +
                "电话：" + phoneLabel + '\n' +
                "电子邮箱：" + emailLabel + '\n' +
                "微信号：" + weixinLabel + '\n' +
                "特长：" + specialSkillLabel;
    }
}
