
// Controller for the Painter app

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PaintChooserController {

	// enum representing pen sizes
	private enum PenSize {
		SMALL(2), MEDIUM(4), LARGE(6);

		private final int radius;

		PenSize(int radius) {
			this.radius = radius;
		}

		public int getRadius() {
			return radius;
		}
	};

	// instance variables that refer to GUI components

	@FXML
	private RadioButton smallRadioButton;
	@FXML
	private RadioButton mediumRadioButton;
	@FXML
	private RadioButton largeRadioButton;
	@FXML
	private Pane drawingAreaPane;
	@FXML
	private ToggleGroup colorToggleGroup;
	@FXML
	private ToggleGroup sizeToggleGroup;
	@FXML
	private Slider redSlider;

	@FXML
	private Slider greenSlider;
	@FXML
	private Slider blueSlider;
	@FXML
	private Slider alphaSlider;
	@FXML
	private TextField redTextField;
	@FXML
	private TextField greenTextField;
	@FXML
	private TextField blueTextField;
	@FXML
	private TextField alphaTextField;

	private int red = 0;
	private int green = 0;
	private int blue = 0;
	private double alpha = 1.0;

	// instance variables for managing Painter state
	private PenSize radius = PenSize.MEDIUM; // radius of circle
	private Paint brushColor = Color.BLACK; // drawing color

	// set user data for the RadioButtons
	public void initialize() {
		// user data on a control can be any Object

		// bind TextField values to corresponding Slider values
		redTextField.textProperty().bind(redSlider.valueProperty().asString("%.0f"));
		greenTextField.textProperty().bind(greenSlider.valueProperty().asString("%.0f"));
		blueTextField.textProperty().bind(blueSlider.valueProperty().asString("%.0f"));
		alphaTextField.textProperty().bind(alphaSlider.valueProperty().asString("%.2f"));

		// listeners that set Rectangle's fill based on Slider changes
		redSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				red = newValue.intValue();
				brushColor = Color.rgb(red, green, blue, alpha);
			}
		});
		greenSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				green = newValue.intValue();
				brushColor = Color.rgb(red, green, blue, alpha);
			}
		});
		blueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				blue = newValue.intValue();
				brushColor = Color.rgb(red, green, blue, alpha);
			}
		});
		alphaSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				alpha = newValue.doubleValue();
				brushColor = Color.rgb(red, green, blue, alpha);

			}
		});
		redSlider.setUserData(red);
		blueSlider.setUserData(blue);
		greenSlider.setUserData(green);
		alphaSlider.setUserData(alpha);

		smallRadioButton.setUserData(PenSize.SMALL);
		mediumRadioButton.setUserData(PenSize.MEDIUM);
		largeRadioButton.setUserData(PenSize.LARGE);
	}

	// handles drawingArea's onMouseDragged MouseEvent
	@FXML
	private void drawingAreaMouseDragged(MouseEvent e) {
		Circle newCircle = new Circle(e.getX(), e.getY(), radius.getRadius(), brushColor);
		drawingAreaPane.getChildren().add(newCircle);
	}

	// handles size RadioButton's ActionEvents
	@FXML
	private void sizeRadioButtonSelected(ActionEvent e) {
		// user data for each size RadioButton is the corresponding PenSize
		radius = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
	}

	// handles Undo Button's ActionEvents
	@FXML
	private void undoButtonPressed(ActionEvent event) {
		int count = drawingAreaPane.getChildren().size();

		// if there are any shapes remove the last one added
		if (count > 0) {
			drawingAreaPane.getChildren().remove(count - 1);
		}
	}

	// handles Clear Button's ActionEvents
	@FXML
	private void clearButtonPressed(ActionEvent event) {
		drawingAreaPane.getChildren().clear(); // clear the canvas
	}
}
