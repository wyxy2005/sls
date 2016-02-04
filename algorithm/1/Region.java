import java.util.LinkedList;
import java.util.Random;

/**
 * 1维，1米长的路面，每次下一滴雨，每滴雨落到地面上长度是0.01米，落点假设均匀分布，求问下了多少滴雨之后路面会全部湿透，求期望？
 */
public class Region {

	private LinkedList<Interval> mWetRegions = new LinkedList<Interval>();

	void mergeRainDrop(double location) {
		double rainDropRightMargin = Math.min(1d, location + 0.005d);
		double rainDropLeftMargin = Math.max(0d, location - 0.005d);
		mergeInteral(new Interval(rainDropLeftMargin, rainDropRightMargin));
	}

	private void mergeInteral(Interval interval) {
		int size = mWetRegions.size();
		for (int i = 0; i < size; i++) {
			Interval tmp = mWetRegions.get(i);
			int ret = tmp.isOverlaped(interval);
			if (ret == Interval.OVERLAP) {
				tmp.merge(interval);
				if (i + 1 < size) {
					Interval next = mWetRegions.get(i + 1);
					if (interval.isOverlaped(next) == Interval.OVERLAP) {
						mWetRegions.remove(next);
						tmp.merge(next);
					}
				}
				return;
			} else if (ret == Interval.DISJOINT_RIGHT) {
				continue;
			} else {
				mWetRegions.add(i, interval);
				return;
			}
		}
		mWetRegions.add(interval);
	}

	boolean isFullWet() {
		if (mWetRegions.size() == 1) {
			Interval interval = mWetRegions.get(0);
			if (interval.left == 0d && interval.right == 1d) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int max = 100000;
		double sum = 0;
		Random random = new Random();
		for (int i = 0; i < max; i++) {
			Region region = new Region();
			int n = 0;
			while (!region.isFullWet()) {
				n++;
				double location = random.nextDouble();// [0,1)
				// location = 1-location;//(0,1]
				region.mergeRainDrop(location);
			}
			sum += n;
			// System.out.println(n);
		}
		System.out.print("final:" + sum / max);
	}
}
