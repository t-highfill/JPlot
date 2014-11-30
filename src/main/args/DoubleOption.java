package main.args;

public class DoubleOption extends Option<Double> {
	public static final Converter<String, Double> STR_TO_DBL =  new Converter<String, Double>(){
		@Override
		public Double convert(String f) {
			if(f.equals(""+null)){
				return null;
			}
			return Double.parseDouble(f);
		}
	};

	public DoubleOption(String name, Double defVal) {
		super(name, defVal, STR_TO_DBL);
	}

	public DoubleOption(String name, Double defVal, Double val) {
		super(name, defVal, val, STR_TO_DBL);
	}

}
