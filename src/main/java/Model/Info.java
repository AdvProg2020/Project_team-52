package Model;

import java.time.LocalDate;

public class Info implements Cloneable{


    private String subject;

    private FieldList list;

    private LocalDate uploadDate;



    public String getSubject() {
        return subject;
    }

    public FieldList getList() {
        return list;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }



    public Info setList(FieldList list) {
        this.list = list;
        return this;
    }



    public Info(String subject, FieldList list, LocalDate uploadDate) {
        this.subject = subject;
        this.list = list;
        this.uploadDate = uploadDate;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        FieldList fieldList = (FieldList) list.clone();
        return ((Info) super.clone()).setList(fieldList);
    }

    @Override
    public String toString() {
        return "Info{" +
                "subject='" + subject + '\'' +
                ", list=" + list +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
