package jp.co.isopra.lunchmap.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long image_id;

    @Column
    @NotNull
    private Date created_time;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Shop shop;


    public Image() {
//      super(); //なにこれ？これ消しても問題なく登録できる、
        this.created_time = new Date();
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public long getImage_id() {
        return image_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

}