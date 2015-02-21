package com.focustar.entity;

public class TMbNews implements java.io.Serializable{

	private int id;
	private String title;		//标题
	private String author;		//作者
	private String coverPage;	//封面 
	private String description;	//摘要
	private String mainText; 	//正文
	private String htmlName;	//html文件名 
	private int groupId; //分公司id
	private int rank;
	
	private Integer parentId;
	private int newsType;
	private int flag = 0;
	private int state = 0;
	
	private String keyword;
	private String md5keyword;
	
	private Activity activity;//活动
	
	
	/**
	 * @category 标记是否签到消息
	 */
	private transient boolean isSign;
	
	public TMbNews(){
		
	}
	
	public TMbNews(boolean isSign){
		this.isSign = isSign;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCoverPage() {
		return coverPage;
	}
	public void setCoverPage(String coverPage) {
		this.coverPage = coverPage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMainText() {
		return mainText;
	}
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	public String getHtmlName() {
		return htmlName;
	}
	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getNewsType() {
		return newsType;
	}
	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public boolean isSign() {
		return isSign;
	}

	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMd5keyword() {
		return md5keyword;
	}

	public void setMd5keyword(String md5keyword) {
		this.md5keyword = md5keyword;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
	
	
	
}
