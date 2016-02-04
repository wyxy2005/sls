class Interval {
	final static int DISJOINT_LEFT = 0;
	final static int DISJOINT_RIGHT = 1;
	final static int OVERLAP = 2;
	double left;
	double right;

	public Interval(double l, double r) {
		if (l >= r) {
			throw new RuntimeException();
		}
		left = l;
		right = r;
	}

	public int isOverlaped(Interval i) {
		if (this.left > i.right) {
			return DISJOINT_LEFT;
		} else if (this.right < i.left) {
			return DISJOINT_RIGHT;
		} else {
			return OVERLAP;
		}
	}

	public void merge(Interval i) {
		if (this.left > i.right) {
			return;
		} else if (this.right < i.left) {
			return;
		} else {
			if (this.right >= i.left) {
				this.left = Math.min(this.left, i.left);
				this.right = Math.max(this.right, i.right);
			}
		}
	}
}
