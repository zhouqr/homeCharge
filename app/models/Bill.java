package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * 结算账单
 * @author zhou
 *
 */
@Entity
public class Bill extends Model{
	/**
	 * 共计
	 */
	@Column
	public float totalMoney = 0;
}
