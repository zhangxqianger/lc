import com.sun.org.apache.xpath.internal.operations.String;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/14.
 */
public class Goods implements Serializable {
    private Integer goodsId;
    private String goodsName;
    private Double goodsPrice;
    private String goodsRemark;

    public Goods() {
    }

    public Goods(Integer goodsId, String goodsName, Double goodsPrice, String goodsRemark) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsRemark = goodsRemark;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    @Override
    public java.lang.String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", goodsPrice=" + goodsPrice +
                ", goodsRemark=" + goodsRemark +
                '}';
    }
}
