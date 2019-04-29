import java.util.Random;

public class Main {
	private static class DesignSpace {
		public int getDimension() {
			return 100;
		}
	}

	private static class SearchPoint {
		private static final double[] INSTNCE = new double[100];

		public double[] getLocation() {
			return INSTNCE;
		}
	}

	private static class ProblemEncoder {
		private static final DesignSpace INSTNCE = new DesignSpace();

		public DesignSpace getDesignSpace() {
			return INSTNCE;
		}
	}

	private static class Library {
		private static final SearchPoint INSTNCE = new SearchPoint();

		public SearchPoint getGbest() {
			return INSTNCE;
		}
	}

	private static class BasicPoint {
		private static final double[] INSTNCE = new double[100];

		public double[] getLocation() {
			return INSTNCE;
		}
	}

	private static class RandomGenerator {
		private static final Random PRNG = new Random();

		public static int intRangeRandom(int min, int max) {
			return min + PRNG.nextInt(max - min + 1);
		}
	}

	private final double CR = 0.9;
	private final double FACTOR = 0.5;

	private final Library socialLib = new Library();

	private final SearchPoint pbest_t = new SearchPoint();

	private final BasicPoint[] POINTS = { new BasicPoint(), new BasicPoint(), new BasicPoint() };

	private BasicPoint[] getReferPoints() {
		return POINTS;
	}

	public void generateBehavior(SearchPoint trailPoint, ProblemEncoder problemEncoder) {
		SearchPoint gbest_t = socialLib.getGbest();

		BasicPoint[] referPoints = getReferPoints();
		int DIMENSION = problemEncoder.getDesignSpace().getDimension();
		int rj = RandomGenerator.intRangeRandom(0, DIMENSION - 1);
		for (int k = 0; k < DIMENSION; k++) {
			if (Math.random() < CR || k == DIMENSION - 1) {
				double Dabcd = 0;
				for (int i = 0; i < referPoints.length; i++) {
					Dabcd += Math.pow(-1, i % 2) * referPoints[i].getLocation()[rj];
				}
				trailPoint.getLocation()[rj] = gbest_t.getLocation()[rj] + FACTOR * Dabcd;
			} else {
				trailPoint.getLocation()[rj] = pbest_t.getLocation()[rj];
			}
			rj = (rj + 1) % DIMENSION;
		}
	}

	public void generateBehavior2(SearchPoint trailPoint, ProblemEncoder problemEncoder) {
		SearchPoint gbest_t = socialLib.getGbest();

		BasicPoint[] referPoints = getReferPoints();
		int DIMENSION = problemEncoder.getDesignSpace().getDimension();
		int rj = RandomGenerator.intRangeRandom(0, DIMENSION - 1);
		for (int k = 0; k < DIMENSION; k++) {
			if (Math.random() < CR || k == DIMENSION - 1) {
				double Dabcd = 0;
				for (int i = 0; i < referPoints.length; i++) {
					Dabcd += (i % 2 == 0 ? +1D : -1D) * referPoints[i].getLocation()[rj];
				}
				trailPoint.getLocation()[rj] = gbest_t.getLocation()[rj] + FACTOR * Dabcd;
			} else {
				trailPoint.getLocation()[rj] = pbest_t.getLocation()[rj];
			}
			rj = (rj + 1) % DIMENSION;
		}
	}

	public void generateBehavior3(SearchPoint trailPoint, ProblemEncoder problemEncoder) {
		SearchPoint gbest_t = socialLib.getGbest();

		BasicPoint[] referPoints = getReferPoints();
		int DIMENSION = problemEncoder.getDesignSpace().getDimension();
		int rj = RandomGenerator.intRangeRandom(0, DIMENSION - 1);
		double sign = +1D;
		for (int k = 0; k < DIMENSION; k++) {
			if (Math.random() < CR || k == DIMENSION - 1) {
				double Dabcd = 0;
				for (int i = 0; i < referPoints.length; i++) {
					Dabcd += sign * referPoints[i].getLocation()[rj];
				}
				trailPoint.getLocation()[rj] = gbest_t.getLocation()[rj] + FACTOR * Dabcd;
			} else {
				trailPoint.getLocation()[rj] = pbest_t.getLocation()[rj];
			}
			rj = (rj + 1) % DIMENSION;
			sign *= -1D;
		}
	}

	public static void main(String[] args) {
		for (int i = 0, length = 1000; i < length; i++) {
			if ((Math.pow(-1, i % 2)) != (i % 2 == 0 ? +1D : -1D)) {
				System.err.println(i);
			}
		}

		Main executable = new Main();

		SearchPoint point = new SearchPoint();
		ProblemEncoder encoder = new ProblemEncoder();

		final long NUMBER_OF_EXPERIMENTS = 100_000L;

		/* First experiment. */ {
			long start = System.currentTimeMillis();
			for (long g = 0; g < NUMBER_OF_EXPERIMENTS; g++) {
				executable.generateBehavior(point, encoder);
			}
			long stop = System.currentTimeMillis();
			System.out.println(stop - start);
		}

		/* Second experiment. */ {
			long start = System.currentTimeMillis();
			for (long g = 0; g < NUMBER_OF_EXPERIMENTS; g++) {
				executable.generateBehavior2(point, encoder);
			}
			long stop = System.currentTimeMillis();
			System.out.println(stop - start);
		}

		/* Third experiment. */ {
			long start = System.currentTimeMillis();
			for (long g = 0; g < NUMBER_OF_EXPERIMENTS; g++) {
				executable.generateBehavior3(point, encoder);
			}
			long stop = System.currentTimeMillis();
			System.out.println(stop - start);
		}
	}
}