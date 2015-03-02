package com.attilax.designpatter.parterr;

import java.util.List;

import com.attilax.frontchk.IFrontCheck;
import com.attilax.json.JsonUtil4jackjson;
import com.attilax.review.AlreadyReviewPassedEx;
import com.attilax.review.IReview;
import com.attilax.review.NoFiltEx;
import com.attilax.review.ReviewBackEx;
import com.attilax.review.ReviewHistry;
import com.attilax.review.ReviewX;
import com.attilax.review.TargetObj;

public class t implements IReview {

	public t() {
		this.ReltDataCheckX1 = new IReltDataCheckX() {

			@Override
			public void checkRelt_shouldBeNoRelt(int i) throws HasReltDataEx {
				if (i == 5)
					throw new HasReltDataEx("HasReltDataEx");

			}
		};
		// this.revChkX=new IReviewCheckX() {
		//
		// @Override
		// public void reviewPassedCheck_shouldBeYetNoReviewOrBack(int i) {
		// // TODO Auto-generated method stub
		//
		// }
		// };
	}

	IReltDataCheckX ReltDataCheckX1;

	// IReviewCheckX revChkX;
	public static void main(String[] args) {
		System.out.println(new t().multiDel_byDwr());
	}

	private String multiDel_byDwr() {
		try {
			return multiDel();
		} catch (PartProcessErrEx e) {
			e.setTypex("PartProcessErrEx");
			return toJsonStr(e);
		}
		// return toJsonStr(new PartProcessErrEx(pex.li, "PartProcessErrEx"));

	}

	private String multiDel() throws PartProcessErrEx {
		PartErrX pex = new PartErrX();
		for (int i = 0; i < 10; i++) {
			try {
				del(i);

			} catch (Exception e) {
				ErrorItem ei = new ErrorItem();
				ei.id = String.valueOf(i);
				ei.msg = e.getMessage();
				pex.add(ei);

			}
		}
		if (pex.li.size() == 0)
			return "ok";

		throw (new PartProcessErrEx(JsonUtil4jackjson.buildNormalBinder()
				.toJson(pex.li)));

	}

	private String toJsonStr(Object obj) {

		return JsonUtil4jackjson.buildNormalBinder().toJson(obj);
	}

	IFrontCheck frtchk;

	private void del(int i) throws HasReltDataEx, AlreadyReviewPassedEx,Exception {
		if (frtchk != null) //  default can b use front chkx...only exist chkor ,then use chkor.. todox ob7
			frtchk.execute(i);
		//ReviewX.reviewPassedCheck_shouldBeYetNoReviewOrBack(i);
		if (ReltDataCheckX1 != null)
			ReltDataCheckX1.checkRelt_shouldBeNoRelt(i);

		if (i == 8)
			throw new RuntimeException("unkk");

	}

	public void editAftSave(int i) {

	}
 /**
  * 4dwr
  * @param obj
  * @return
  */
	public boolean reviewPassedCheck_dwr(Object obj)   {
//		try {
//		//	ReviewX.reviewPassedCheck_shouldBeYetNoReviewOrBack((Integer) obj);
//		} catch (AlreadyReviewPassedEx e) {
//			return true;
//		}
		return false;
	}

 

 

	/* (non-Javadoc)
	 * @see com.attilax.review.IReview#setReviewObj4List(java.util.List)
	 */
	@Override
	public void setReviewObj4List(List data) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.attilax.review.IReview#convertObj2Targetobj(com.attilax.review.ReviewHistry)
	 */
	@Override
	public TargetObj convertObj2Targetobj(ReviewHistry bizobj) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.attilax.review.IReview#reviStateFiltedIds(java.lang.Object)
	 */
	@Override
	public String reviStateFiltedIds(Object p) throws NoFiltEx {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.attilax.review.IReview#setReviewPassedPropFilter(java.lang.Object)
	 */
	@Override
	public Object setReviewPassedPropFilter(Object p) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.attilax.review.IReview#hasReviewAuth(java.lang.Object)
	 */
	@Override
	public boolean hasReviewAuth(Object p) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.attilax.review.IReview#reviewPassedCheck(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean reviewPassedCheck(Object obj, String dataType)
			throws AlreadyReviewPassedEx, ReviewBackEx {
		// TODO Auto-generated method stub
		return false;
	}

	 

}
