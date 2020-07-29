package Model;

import Model.Data.Data;
import Model.DataBase.DataBase;
import Model.Interface.AddingNew;
import Model.Interface.Packable;
import Exception.*;

import java.util.Collections;
import java.util.List;

public class Score implements Packable<Score> {
    private static List<Score> list= new ArrayList<>();

    private long scoreId;
    private long userId;
    private long goodId;
    private int scoreNum;

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }

    public static void setList(List<Score> list) {
        Score.list = list;
    }

    @Override
    public long getId() {
        return scoreId;
    }


    public static List<Score> getList() {
        return Collections.unmodifiableList(list);
    }

    public static void addScore( Score score) {
        score.setScoreId(AddingNew.getRegisteringId().apply(Score.getList()));
        list.add(score);
        DataBase.save(score,true);
    }

    public static void removeScore(Score score) {
        list.removeIf(sco -> score.getId() == sco.getId());
        DataBase.remove(score);
    }

    @Override
    public Data<Score> pack() {
        return new Data<Score>()
                .addField(scoreId)
                .addField(userId)
                .addField(goodId)
                .addField(scoreNum)
                .setInstance(new Score());
    }

    @Override
    public Score dpkg(Data<Score> data) {
        return null;
    }


    public static Score getScoreById(long id) throws ScoreDoesNotExistException {
        return list.stream()
                .filter(score -> id == score.getId())
                .findFirst()
                .orElseThrow(() -> new ScoreDoesNotExistException(
                        "Score with the id:" + id + " doesn't exist in list of total scores")
                );
    }


    public Score(long userId, long goodId, int scoreNum) {
        this.userId = userId;
        this.goodId = goodId;
        this.scoreNum = scoreNum;
    }

    private Score() {
    }


    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", scoreNum=" + scoreNum +
                '}';
    }


}
