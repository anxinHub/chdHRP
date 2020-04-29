package com.chd.hrp.pac.entity.basicset.warning;


public class PactWarnSetFKHTEntity extends BaseWarnEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8183419904812336923L;

	private Integer deposit_ret_w;
	private Integer pay_w;

	public Integer getDeposit_ret_w() {
		return deposit_ret_w;
	}

	public void setDeposit_ret_w(Integer deposit_ret_w) {
		this.deposit_ret_w = deposit_ret_w;
	}

	public Integer getPay_w() {
		return pay_w;
	}

	public void setPay_w(Integer pay_w) {
		this.pay_w = pay_w;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
