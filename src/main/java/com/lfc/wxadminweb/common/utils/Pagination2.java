package com.lfc.wxadminweb.common.utils;

import java.util.List;
import java.util.Map;

/**
 * 用于分页的工具类
 * 
 * @author 
 */
public class Pagination2<T> {

	private List<T> list; // 对象记录结果集
	private int total = 0; // 总记录数
	private int limit = 10; // 每页显示记录数
	private int pages = 1; // 总页数
	private int pageNumber = 1; // 当前页

	private boolean isFirstPage = false; // 是否为第一页
	private boolean isLastPage = false; // 是否为最后一页
	private boolean hasPreviousPage = false; // 是否有前一页
	private boolean hasNextPage = false; // 是否有下一页

	private int navigatePages = 10; // 导航页码数
	private int[] navigatePageNumbers; // 所有导航页号

	
	public Pagination2() {
	}
	
	public Pagination2(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Pagination2(int pageNumber, int limit) {
		this.pageNumber = pageNumber;
		this.limit = limit;
	}

	//设置完total数量就init初始化
	public void init() {
		// 设置基本参数
		this.pages = (this.total - 1) / this.limit + 1;

		// 根据输入可能错误的当前号码进行自动纠正
		if (this.pageNumber < 1) {
			this.pageNumber = 1;
		} else if (pageNumber > this.pages) {
			this.pageNumber = this.pages;
		}

		// 基本参数设定之后进行导航页面的计算
		calcNavigatePageNumbers();

		// 以及页面边界的判定
		judgePageBoudary();
	}

	/**
	 * 计算导航页
	 */
	public void calcNavigatePageNumbers() {
		// 当总页数小于或等于导航页码数时
		if (pages <= navigatePages) {
			navigatePageNumbers = new int[pages];
			for (int i = 0; i < pages; i++) {
				navigatePageNumbers[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatePageNumbers = new int[navigatePages];
			int startNum = pageNumber - navigatePages / 2;
			int endNum = pageNumber + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navPageCount页
				for (int i = 0; i < navigatePages; i++) {
					navigatePageNumbers[i] = startNum++;
				}
			} else if (endNum > pages) {
				endNum = pages;
				// 最后navPageCount页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatePageNumbers[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatePageNumbers[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 判定页面边界
	 */
	public void judgePageBoudary() {
		isFirstPage = pageNumber == 1;
		isLastPage = pageNumber == pages;
		hasPreviousPage = pageNumber != 1;
		hasNextPage = pageNumber != pages;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 得到当前页的内容
	 * 
	 * @return {List}
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 得到记录总数
	 * 
	 * @return {int}
	 */
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 得到每页显示多少条记录
	 * 
	 * @return {int}
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 得到页面总数
	 * 
	 * @return {int}
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * 得到当前页号
	 * 
	 * @return {int}
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 得到所有导航页号
	 * 
	 * @return {int[]}
	 */
	public int[] getNavigatePageNumbers() {
		return navigatePageNumbers;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	public boolean getHasNextPage() {
		return hasNextPage;
	}
	
	/**
	 * 导航页号是否靠左(最小页码是否为1页)，用来判断是否显示“首页”按钮
	 * 
	 */
	public boolean isLeft(){
		return navigatePageNumbers[0] == 1;
	}
	
	/**
	 * 导航页号是否靠右(最大页码是否为末页)，用来判断是否显示“末页”按钮
	 * 
	 */
	public boolean isRight(){
		return navigatePageNumbers[navigatePageNumbers.length - 1] == pages;
	}

}
