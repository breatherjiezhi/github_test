package com.dhc.rad.modules.pzScoreLog.entity;


import com.dhc.rad.common.persistence.DataEntity;

/**
 * @author 10951
 */

public class PzScoreLog extends DataEntity<PzScoreLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 积分变动用户id
     */
    private String userId;

    /**
     * 餐厅id
     */
    private String restaurantId;


    /**
     * 积分类型： 0扣分 1加分 2转换积分
     */
    private Integer scoreType;

    /**
     * 积分变动描述
     */
    private String scoreDescription;

    /**
     * 变动积分(+1/-1)
     */
    private String scoreChange;

    /**
     *积分分类 0 卡券 1 积分
     */
    private Integer scoreClassify;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getScoreClassify() {
        return scoreClassify;
    }

    public void setScoreClassify(Integer scoreClassify) {
        this.scoreClassify = scoreClassify;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreDescription() {
        return scoreDescription;
    }

    public void setScoreDescription(String scoreDescription) {
        this.scoreDescription = scoreDescription;
    }

    public String getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(String scoreChange) {
        this.scoreChange = scoreChange;
    }
}
