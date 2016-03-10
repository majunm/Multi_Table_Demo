package test;

import java.util.List;
import java.util.Random;

import android.test.AndroidTestCase;
import android.util.Log;

import com.db.TestBeanDaoImpl;
import com.domain.TestBean;

public class Test extends AndroidTestCase {
	public void testInsert() {
		TestBeanDaoImpl dao = TestBeanDaoImpl.getInstance().init(getContext());
		String[] ss = { "郭山彤", "梁敦厦", "霍负浪", "虞信品", "马仁毅", "冯州龙", "简务帅", "黎丙赣",
				"谢尉争", "赵单羽", "孟航沛", "龚开梦", "黄蓝风", "易堃登", "蔡农仲", "高洪泉", "巫家昱",
				"陈迎梅", "陈蓓斯", "陈琬颖", "陈方良", "陈鹭晰", "陈光华", "陈若琪", "陈星竹", "陈佳茵",
				"陈廷鸿", "陈  义", "陈思谕", "陈善国", "陈和玫", "陈炳泽", "陈昱行", "陈  禧",
				"陈叶媚", "陈  齐", "陈亦灵" };
		String[] sexs = { "男", "女" };
		String[] hobbys = { "篮球", "足球", "台球", "乒乓球", "羽毛球", "网球", "高尔夫球", "棒球",
				"垒球", "保龄球", "毽球", "板球", "橄榄球", "曲棍球", "弹球" };
		for (int i = 0; i < 40; i++) {
			TestBean m = new TestBean();
			m.setName(ss[new Random().nextInt(ss.length)]);
			m.setSex(sexs[new Random().nextInt(sexs.length)]);
			m.setAge(new Random().nextInt(50) + 10);
			m.setHobby(hobbys[new Random().nextInt(hobbys.length)]);
			m.setId(new Random().nextInt(i + 1) + 4);
			long insert = dao.insert(m);
			Log.e("JULY", "insert=" + insert);
		}
	}

	// 不支持 =========================
	public void testRightJoin() {
		TestBeanDaoImpl dao = TestBeanDaoImpl.getInstance().init(getContext());
		List<TestBean> rightJoinQuery = dao.rightJoinQuery();
		for (TestBean testBean : rightJoinQuery) {
			Log.e("JULY", "testBean=" + testBean.toString());
		}
	}

	// 支持左外链接 =========================
	public void testLeftJoin() {
		TestBeanDaoImpl dao = TestBeanDaoImpl.getInstance().init(getContext());
		List<TestBean> rightJoinQuery = dao.iQuery(0);
		Log.e("JULY", "====================");
		for (TestBean testBean : rightJoinQuery) {
			Log.e("JULY", "testBean=" + testBean.toString());
		}
		Log.e("JULY", "====================");
		if (rightJoinQuery != null) {
			Log.e("JULY", "====================" + rightJoinQuery.size());
		}
	}

	public void testInnerJoin() {
		TestBeanDaoImpl dao = TestBeanDaoImpl.getInstance().init(getContext());
		List<TestBean> rightJoinQuery = dao.iQuery(3);
		Log.e("JULY", "====================");
		for (TestBean testBean : rightJoinQuery) {
			Log.e("JULY", "testBean=" + testBean.toString());
		}
		Log.e("JULY", "====================");
		if (rightJoinQuery != null) {
			Log.e("JULY", "====================" + rightJoinQuery.size());
		}
	}

	public void testCrossJoin() {
		TestBeanDaoImpl dao = TestBeanDaoImpl.getInstance().init(getContext());
		List<TestBean> rightJoinQuery = dao.iQuery(4);
		Log.e("JULY", "====================");
		for (TestBean testBean : rightJoinQuery) {
			Log.e("JULY", "testBean=" + testBean.toString());
		}
		Log.e("JULY", "====================");
		if (rightJoinQuery != null) {
			Log.e("JULY", "====================" + rightJoinQuery.size());
		}
	}

	public void testFindById() {
		TestBeanDaoImpl dao = TestBeanDaoImpl.getInstance().init(getContext());
		TestBean rightJoinQuery = dao.findBeanById(1);
		if (rightJoinQuery != null) {
			Log.e("JULY", "====================" + rightJoinQuery.toString());
		} else {
			Log.e("JULY", "===========null=========");
			
		}
	}
}
