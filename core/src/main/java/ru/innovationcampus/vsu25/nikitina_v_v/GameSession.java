package ru.innovationcampus.vsu25.nikitina_v_v;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import ru.innovationcampus.vsu25.nikitina_v_v.managers.MemoryManager;

public class GameSession {
    long nextIceCreamSpawnTime;
    long nextCloudSpawnTime;
    long nextStarSpawnTime;
    long sessionStartTime;
    long pauseStartTime;
    public int score;
    public int destructedTrashNumber;
    public GameState state;

    public GameSession() {}

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx,getScore());
        MemoryManager.saveTableOfRecords(recordsTable);
    }
    public void destructionRegistration() {
        destructedTrashNumber +=1;
    }
    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100;
    }
    public int getScore() {
        return score;
    }
    public void startGame() {
        sessionStartTime = TimeUtils.millis();
        state = GameState.PLAYING;
        nextIceCreamSpawnTime = sessionStartTime +
            (long)(GameSettings.STARTING_ICE_CREAM_COOL_DOWN * getTrashPeriodCoolDown());
        nextCloudSpawnTime = sessionStartTime +
            (long)(GameSettings.STARTING_CLOUD_COOL_DOWN * getTrashPeriodCoolDown());
        nextStarSpawnTime = sessionStartTime +
            (long)(GameSettings.STARTING_STAR_COOL_DOWN * getTrashPeriodCoolDown());
    }
    public void pauseGame(){
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }
    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }
    public boolean shouldSpawnIceCream() {
        if (nextIceCreamSpawnTime <= TimeUtils.millis()) {
            nextIceCreamSpawnTime = (TimeUtils.millis() + (long) (GameSettings.STARTING_ICE_CREAM_COOL_DOWN * getTrashPeriodCoolDown()))+5000;
            return true;
        }
        return false;
    }
    public boolean shouldSpawnCloud() {
        if (nextCloudSpawnTime <= TimeUtils.millis()) {
            nextCloudSpawnTime = (TimeUtils.millis() + (long) (GameSettings.STARTING_CLOUD_COOL_DOWN * getTrashPeriodCoolDown()))+5000;
            return true;
        }
        return false;
    }
    public boolean shouldSpawnStar() {
        if (nextStarSpawnTime <= TimeUtils.millis()) {
            nextStarSpawnTime = (TimeUtils.millis() + (long) (GameSettings.STARTING_STAR_COOL_DOWN * getTrashPeriodCoolDown()))+5000;
            return true;
        }
        return false;
    }
    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime) / 5000);
    }

}
