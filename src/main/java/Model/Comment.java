package Model;

import Exception.*;

import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.Packable;

import java.util.Collections;
import java.util.List;

public class Comment implements Packable<Comment> {
    private static List<Comment> list=new ArrayList<>();

    private long commentId;
    private String pendStatus;
    private long userId;
    private long goodId;
    private FieldList fieldList;

    private Comment() {

    }


    public long getId() {
        return commentId;
    }
    public FieldList getFieldList() {
        return fieldList;
    }

    public long getGoodId() {
        return goodId;
    }

    public long getUserId() {
        return userId;
    }

    public static List<Comment> getList() {
        return Collections.unmodifiableList(list);
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public static void setList(List<Comment> list) {
        Comment.list = list;
    }

    public static void addComment( Comment comment) {
        comment.setCommentId(AddingNew.getRegisteringId().apply(Comment.getList()));
        list.add(comment);
        DataBase.save(comment, true);
    }

    public static Comment getCommentById(long id) throws CommentDoesNotExistException {
        return list.stream()
                .filter(comment -> id == comment.getId())
                .findFirst()
                .orElseThrow(() -> new CommentDoesNotExistException(
                        "Comment whit the id:" + id + " does not exist in list of all comments."
                ));
    }


    @Override
    public Data<Comment> pack() {
        return new Data<Comment>()
                .addField(commentId)
                .addField(pendStatus)
                .addField(userId)
                .addField(goodId)
                .addField(fieldList)
                .setInstance(new Comment());
    }
    @Override
    public Comment dpkg(Data<Comment> data) {
        this.commentId = (long) data.getFields().get(0);
        this.pendStatus = (String) data.getFields().get(1);
        this.userId = (long) data.getFields().get(2);
        this.goodId = (long) data.getFields().get(3);
        this.fieldList = (FieldList) data.getFields().get(4);
        return this;
    }



    public Comment(String pendStatus, long userId, long goodId, FieldList fieldList) {
        this.pendStatus = pendStatus;
        this.userId = userId;
        this.goodId = goodId;
        this.fieldList = fieldList;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", pendStatus='" + pendStatus + '\'' +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", fieldList=" + fieldList +
                '}';
    }


}
