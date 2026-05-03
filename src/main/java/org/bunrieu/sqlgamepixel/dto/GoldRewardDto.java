package org.bunrieu.sqlgamepixel.dto;

public class GoldRewardDto {
    private int goldEarned;
    private int totalGold;
    private String reason;
    private String message;

    public int getGoldEarned() { return goldEarned; }
    public void setGoldEarned(int goldEarned) { this.goldEarned = goldEarned; }

    public int getTotalGold() { return totalGold; }
    public void setTotalGold(int totalGold) { this.totalGold = totalGold; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
