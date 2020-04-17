package tes;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;

import static javafx.application.Application.launch;


public class controller implements Initializable {

	@FXML
	private ColorPicker fillPickerButton = new ColorPicker();

	@FXML
	private MenuItem nonStrockButton,num1Strock,num2Strock;

	@FXML
	private Button STFButton;

	@FXML
	private Button STBButton;

	@FXML
	private MenuItem rotateRight,rotateLeft;


	@FXML
	private AnchorPane canvas;

	@FXML
	private ColorPicker colorButton= new ColorPicker();

	@FXML
	AnchorPane CircleP , RectangleP , TriangleP , SquareP , EllipseP , LineP;

	@FXML
	private TextField C_X,C_Y,C_R,C_S;


	@FXML
	private TextField E_X,E_Y,E_R1,E_R2,E_S;

	@FXML
	private TextField T_Y1,T_X1,T_S,T_Y2,T_X2,T_Y3,T_X3;

	@FXML
	private TextField S_X,S_Y,S_I,S_S;

	@FXML
	private TextField L_X1,L_Y1,L_S,L_X2,L_Y2;

	@FXML
	private TextField R_X,R_Y,R_W,R_L,R_S;

	@FXML
	ToggleButton freelyDrawButton,circleButton,squareButton,triangleButton,elipseButton,rectangleButton,lineButton= new ToggleButton();

	Shape active;
	saver saver;

	//refers to the coordinates where the shape has been created
	double refX = 0;
	double refY = 0;


	@Override

	public void initialize(URL location, ResourceBundle resources) {



		colorButton.setValue(Color.BLACK);
		fillPickerButton.setValue(Color.BLACK);
	}

    /**
     * draw method is used to set up the drawing process on the canvas
     * @param event returns the user click which can get all the information about
     *              the canvas and the shape on it.
     */
	public void draw(MouseEvent event) {

		try {
			if (event.getButton() == MouseButton.SECONDARY) {
				select(event);
				return;
			}
			try {
                active.getStrokeDashArray().clear();
            }catch (Exception e){}
			if (freelyDrawButton.isSelected()) {
				active = new Circle(event.getX(), event.getY(), 10);

			} else if (rectangleButton.isSelected())

				active = new Rectangle(event.getX(), event.getY(), 0, 0);
			else if (circleButton.isSelected()) {

				active = new Circle(event.getX(), event.getY(), 0);
			} else if (elipseButton.isSelected()) {


				active = new Ellipse(event.getX(), event.getY(), 0, 0);
			} else if (triangleButton.isSelected()) {


				active = new Polygon(event.getX(), event.getY(), event.getX(), event.getY(), event.getX(), event.getY());
			} else if (squareButton.isSelected()) {

				active = new Rectangle(event.getX(), event.getY(), 0, 0);
			} else if (lineButton.isSelected()) {

				active = new Line(event.getX(), event.getY(), event.getX(), event.getY());
				active.setStrokeWidth(5);
			}
			active.setCursor(Cursor.MOVE);
			active.setStroke(colorButton.getValue());
			active.setFill(fillPickerButton.getValue());
			active.setStrokeWidth(5);
			active.getStrokeDashArray().clear();
            active.getStrokeDashArray().addAll(25d,10d);
			select(event);
			refX = 0;
			refY = 0;


			canvas.getChildren().add(active);
		}catch (Exception e){

		}

	}

    /**
     * exports a screenshot of the canvas as .png or .jpeg file
     */
	public void saveAsPicture() {

		final FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("Untitled");
		FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
		FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JBEG", "*.jbg", "*.jbeg");
		fileChooser.getExtensionFilters().addAll(pngFilter, jpegFilter);


		WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

		File file = fileChooser.showSaveDialog(Main.getStage());

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    /**
     * exports a .dat file that can be used later for editing.
     */

	public void saveFile() {

		try {
			final FileChooser fileChooser = new FileChooser();

			fileChooser.setInitialFileName("Untitled");
			FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("DAT","*.dat");
			fileChooser.getExtensionFilters().addAll(jpegFilter);

			File file = fileChooser.showSaveDialog(Main.getStage());

			saver = new saver();
			int i = 0;
			for (Node x : canvas.getChildren()) {

				shape temp=null;
				if (x.getClass()== Rectangle.class)
				{
					Rectangle y = (Rectangle) x;

					temp = new savedRectangle(y.getX(), y.getY(), y.getFill().toString(), y.getStroke().toString(), y.getStrokeWidth(), y.getHeight(), y.getWidth());

				}else if (x.getClass()== Circle.class) {
					Circle y = (Circle) x;
					temp = new savedCircle(y.getCenterX(), y.getCenterY(), y.getFill().toString(), y.getStroke().toString(), y.getStrokeWidth(),y.getRadius());


				}else if (x.getClass()== Ellipse.class){
					Ellipse y = (Ellipse) x;
					temp = new savedEllipse(y.getCenterX(), y.getCenterY(), y.getFill().toString(), y.getStroke().toString(), y.getStrokeWidth(),y.getRadiusX(),y.getRadiusY());


				}else if (x.getClass()== Polygon.class){
                    Polygon y = (Polygon) x;
                    temp = new savedTriangle(y.getPoints().get(0),y.getPoints().get(1),y.getPoints().get(2),y.getPoints().get(3),y.getPoints().get(4),y.getPoints().get(5), y.getFill().toString(), y.getStroke().toString(), y.getStrokeWidth());

				}else if (active.getClass()== Line.class){
					Line y = (Line) x;
					temp = new savedLine(y.getStartX(), y.getStartY(), y.getFill().toString(), y.getStroke().toString(), y.getStrokeWidth(),y.getEndX(),y.getEndY());


				}

				saver.shapesCollection.add(temp);
			}
            if (file!=null) {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(saver);
                System.out.println("done saving");

                out.close();
            }
		} catch (FileNotFoundException e) {
			System.out.println("not found");

		} catch (IOException e) {
			// TODO Auto-generated catch block



			System.out.println("out  of file");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    /**
     * opens a .dat file which can be edited as appropriate
     */
	public void open() {

		final FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("DAT", "*.dat");
		fileChooser.getExtensionFilters().add(datFilter);

		File file = fileChooser.showOpenDialog(Main.getStage());
		if (file != null) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				Object a = in.readObject();

				saver = (saver) a;
				canvas.getChildren().clear();


				for ( Object x : saver.shapesCollection) {

					Shape temp = null;
					if (x.getClass() == savedRectangle.class) {
						savedRectangle y = (savedRectangle) x;

						temp = new Rectangle(y.getX(), y.getY(),y.getWidth(),y.getLength());
					} else if (x.getClass() == savedCircle.class) {
						savedCircle y = (savedCircle) x;
						temp = new Circle(y.getX(), y.getY(),y.getRadius());


					} else if (x.getClass() == savedEllipse.class) {

						savedEllipse y = (savedEllipse) x;
						temp = new Ellipse(y.getX(), y.getY(),y.getRadiusY(),y.getRadiusX());

					} else if (x.getClass() == savedTriangle.class) {
                        savedTriangle y = (savedTriangle) x;
                        temp = new Polygon(y.getX1(),y.getY1(),y.getX2(),y.getY2(),y.getX3(),y.getY3());
                        temp.setFill(Color.valueOf(((shape)x).getFillColor()));

					} else if (x.getClass() == savedLine.class) {
						savedLine y = (savedLine) x;
						temp = new Line(y.getX(), y.getY(),y.getEndX(),y.getEndY());

					}
					temp.setFill(Color.valueOf(((shape)x).getFillColor()));
					temp.setStroke(Color.valueOf(((shape)x).getStrokeColor()));
					temp.setStrokeWidth(((shape)x).getStrokeWidth());
					canvas.getChildren().add(temp);
				}

			} catch (FileNotFoundException e) {

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void createCanvas() throws Exception {

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/GUI.fxml"));
		controller controller = new controller();
        loader.setController( controller);
        Parent root = loader.load();
			primaryStage.setScene( new Scene(root));
		primaryStage.setMaximized(true);
		primaryStage.getIcons().add(new Image("Gui/paint.png"));
		primaryStage.setTitle("Painter Shop");
		primaryStage.show();


	}
    /**
     * used for various activities that involves selecting a shape in it.
     * @param event returns the user click which can get all the information about
     * 	     the canvas and the shape on it.
     */

	public void select(MouseEvent event){

		if (event.getTarget()== canvas){
			CircleP.setVisible(false);
			RectangleP.setVisible(false);
			TriangleP.setVisible(false);
			SquareP.setVisible(false);
			EllipseP.setVisible(false);
			LineP.setVisible(false);
			active.getStrokeDashArray().clear();

			return;
		}
        active.getStrokeDashArray().clear();
		active= (Shape)event.getTarget();
		colorButton.setValue(Color.valueOf(active.getStroke().toString()));
		fillPickerButton.setValue(Color.valueOf(active.getFill().toString()));

        active.getStrokeDashArray().addAll(25d,10d);
		
		 if (active.getClass()== Rectangle.class)
		{

			CircleP.setVisible(false);
			RectangleP.setVisible(true);
			TriangleP.setVisible(false);
			SquareP.setVisible(false);
			EllipseP.setVisible(false);
			LineP.setVisible(false);
			active = (Rectangle)event.getTarget();
			R_X.setText(""+ ((Rectangle) active).getX());
			R_Y.setText(""+ ((Rectangle) active).getY());
			R_W.setText(""+ ((Rectangle) active).getWidth());
			R_L.setText(""+ ((Rectangle) active).getHeight());
			R_S.setText(""+ ((Rectangle) active).getStrokeWidth());



		}else if (active.getClass()== Circle.class) {
			CircleP.setVisible(true);
			RectangleP.setVisible(false);
			TriangleP.setVisible(false);
			SquareP.setVisible(false);
			EllipseP.setVisible(false);
			LineP.setVisible(false);
			active = (Circle)event.getTarget();
			C_X.setText(""+ ((Circle) active).getCenterX());
			C_Y.setText(""+ ((Circle) active).getCenterY());
			C_R.setText(""+ ((Circle) active).getRadius());
			C_S.setText(""+ ((Circle) active).getStrokeWidth());


		}else if (active.getClass()== Ellipse.class){
			CircleP.setVisible(false);
			RectangleP.setVisible(false);
			TriangleP.setVisible(false);
			SquareP.setVisible(false);
			EllipseP.setVisible(true);
			LineP.setVisible(false);
			active = (Ellipse)event.getTarget();
			E_X.setText(""+ ((Ellipse) active).getCenterX());
			E_Y.setText(""+ ((Ellipse) active).getCenterY());
			E_R1.setText(""+((Ellipse) active).getRadiusX());
			E_R2.setText(""+((Ellipse) active).getRadiusY());
			E_S.setText(""+ ((Ellipse) active).getStrokeWidth());


		}else if (active.getClass()== Polygon.class){
			CircleP.setVisible(false);
			RectangleP.setVisible(false);
			TriangleP.setVisible(true);
			SquareP.setVisible(false);
			EllipseP.setVisible(false);
			LineP.setVisible(false);
			active = (Polygon)event.getTarget();
			T_X1.setText(""+ ((Polygon) active).getPoints().get(0).toString());
			T_X2.setText(""+ ((Polygon) active).getPoints().get(2));
			T_X3.setText(""+ ((Polygon) active).getPoints().get(4));
			T_Y1.setText(""+ ((Polygon) active).getPoints().get(1));
			T_Y2.setText(""+ ((Polygon) active).getPoints().get(3));
			T_Y3.setText(""+ ((Polygon) active).getPoints().get(5));
			T_S.setText(""+ ((Polygon) active).getStrokeWidth());

		}else if (active.getClass()== Line.class){
			CircleP.setVisible(false);
			RectangleP.setVisible(false);
			TriangleP.setVisible(false);
			SquareP.setVisible(false);
			EllipseP.setVisible(false);
			LineP.setVisible(true);
			active = (Line)event.getTarget();
			L_X1.setText(""+ ((Line) active).getStartX());
			L_Y1.setText(""+ ((Line) active).getStartY());
			L_X2.setText(""+ ((Line) active).getEndX());
			L_Y2.setText(""+ ((Line) active).getEndY());
			L_S.setText(""+ ((Line) active).getStrokeWidth());



		}

	}
    /**
     * used to specify the drag and drop of the various different shapes
     * @param event
     */
	double getTranslateX=0;
	double getTranslateY=0;
	public void drag(MouseEvent event) {
		try {
			
			
			
			if (refX == 0) {
				refX = event.getX();
				refY = event.getY();
				getTranslateX=active.getTranslateX();
				getTranslateY=active.getTranslateY();
			}
			if (event.getButton() == MouseButton.PRIMARY) 
			if(event.getTarget() instanceof Shape) {
				
					
					
				
				active.setTranslateX(getTranslateX+event.getX()-refX);
				active.setTranslateY(getTranslateY+event.getY()-refY);
				return;
			}
			
			if (freelyDrawButton.isSelected()) {
				Circle temp = new Circle(event.getX(), event.getY(), 10);
				temp.setFill(fillPickerButton.getValue());
				temp.setStroke(colorButton.getValue());
				canvas.getChildren().add(temp);
			} else if (active.getClass()==Rectangle.class) {
				Rectangle temp = ((Rectangle) active);
				if (refX > event.getX()) {
					temp.setX(event.getX());
				}
				if (refY > event.getY()) {
					temp.setY(event.getY());
				}
				temp.setHeight(Math.abs(event.getY() - refY));
				temp.setWidth(Math.abs(event.getX() - refX));
			} else if (active.getClass()==Circle.class) {
				Circle temp = ((Circle) active);



				temp.setRadius(Math.max(Math.abs(event.getY() - refY), Math.abs(event.getX() - refX)));

			} else if (active.getClass()==Ellipse.class) {
				Ellipse temp = (Ellipse) active;

				temp.setRadiusX(Math.abs(event.getX() - refX));
				temp.setRadiusY(Math.abs(event.getY() - refY));


			} else if (active.getClass()==Polygon.class) {
                Polygon temp = (Polygon) active;

                double distanceY = Math.abs(refY - event.getY());
                double distanceX = refX - event.getX();
                double p3x = -1 * (event.getX() - refX);


                temp.getPoints().setAll(p3x + refX, event.getY(), event.getX(), event.getY(), refX, refY);


            }else if (active.getClass()==Line.class) {
				Line temp = (Line) active;
				temp.setEndX(event.getX());
				temp.setEndY(event.getY());
			}
			active.getStrokeDashArray().setAll(25d,10d);

		}catch (Exception e){

		}
	}
	//STB = send to back
	@FXML
	void STB(ActionEvent event) {
		active.toBack();
	}

	//	STF = send to front

	@FXML
	void STF(ActionEvent event) {
		active.toFront();
	}

	@FXML
	void color(ActionEvent event) {

		active.setStroke(colorButton.getValue());
		active.setFill(fillPickerButton.getValue());

	}


	@FXML
	void flipHorizontal(ActionEvent event) {
	    if(active.getScaleX()==1)
		active.setScaleX(-1);
	    else {
            active.setScaleX(1);
        }
	}

	@FXML
	void flipVertical(ActionEvent event) {
        if(active.getScaleY()==1)
            active.setScaleY(-1);
        else {
            active.setScaleY(1);
        }
	}

	@FXML
	void resizeStrock(ActionEvent event) {

		if(event.getSource()==num1Strock){
			active.setStrokeWidth(5.0);
			active.setStroke(Color.valueOf(colorButton.getValue().toString()));
		}else if (event.getSource()==num2Strock){
			active.setStrokeWidth(10);
			active.setStroke(Color.valueOf(colorButton.getValue().toString()));
		}else{
			active.setStrokeWidth(0.0);
		}



	}

	@FXML
	void rotate(ActionEvent event) {

//
		if ( event.getSource() == rotateRight ){
			active.setRotate(active.getRotate()+90);
//
		}else if ( event.getSource() == rotateLeft ) {
            active.setRotate(active.getRotate()-90);

		}
	}

	public void exportPicture() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("Untitled");
		FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
		FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JBEG", "*.jbg", "*.jbeg");
		fileChooser.getExtensionFilters().addAll(pngFilter, jpegFilter);


		WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

		File file = fileChooser.showSaveDialog(Main.getStage());

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	void updateCircle(KeyEvent event) {
		Circle temp=(Circle)active;
		temp.setCenterX(Double.parseDouble(C_X.getText()));
		temp.setCenterY(Double.parseDouble(C_Y.getText()));
		temp.setRadius(Double.parseDouble(C_R.getText()));
		temp.setStrokeWidth(Double.parseDouble(C_S.getText()));
	}

	@FXML
	void updateEllipse(KeyEvent event) {
		Ellipse temp=(Ellipse) active;
		temp.setCenterX(Double.parseDouble(E_X.getText()));
		temp.setCenterY(Double.parseDouble(E_Y.getText()));
		temp.setRadiusX(Double.parseDouble(E_R1.getText()));
		temp.setRadiusY(Double.parseDouble(E_R2.getText()));
		temp.setStrokeWidth(Double.parseDouble(E_S.getText()));
	}

	@FXML
	void updateLine(KeyEvent event) {
		Line temp=(Line) active;
		temp.setStartX(Double.parseDouble(L_X1.getText()));
		temp.setStartY(Double.parseDouble(L_Y1.getText()));
		temp.setEndX(Double.parseDouble(L_X2.getText()));
		temp.setEndY(Double.parseDouble(L_Y2.getText()));
		temp.setStrokeWidth(Double.parseDouble(L_S.getText()));
	}

	@FXML
	void updateRectangle(KeyEvent event) {
		Rectangle temp=(Rectangle) active;
		temp.setX(Double.parseDouble(R_X.getText()));
		temp.setY(Double.parseDouble(R_Y.getText()));
		temp.setWidth(Double.parseDouble(R_W.getText()));
		temp.setHeight(Double.parseDouble(R_L.getText()));
		temp.setStrokeWidth(Double.parseDouble(R_S.getText()));

	}

	@FXML
	void updateTriangle(KeyEvent event) {
		Polygon temp=(Polygon) active;
		double x1=Double.parseDouble(T_X1.getText());
		double x2=Double.parseDouble(T_X2.getText());
		double x3=Double.parseDouble(T_X3.getText());
		double y1=Double.parseDouble(T_Y1.getText());
		double y2=Double.parseDouble(T_Y2.getText());
		double y3=Double.parseDouble(T_Y3.getText());
		temp.getPoints().setAll(x1,y1,x2,y2,x3,y3);

		temp.setStrokeWidth(Double.parseDouble(T_S.getText()));
	}
	public void deleteShape(KeyEvent event){
		try {
		if ( event.getCode().equals( KeyCode.DELETE ) )
				canvas.getChildren().remove(active);
		else if ( event.getCode().equals( KeyCode.D ) )
				active.setTranslateX(active.getTranslateX()+3);
		else if ( event.getCode().equals( KeyCode.S ) )
			active.setTranslateY(active.getTranslateY()+3);
		else if ( event.getCode().equals( KeyCode.A ) )
			active.setTranslateX(active.getTranslateX()-3);
		else if ( event.getCode().equals( KeyCode.W ) )
			active.setTranslateY(active.getTranslateY()-3);

		}catch (Exception e){

			}
			return;

	}
	public void reCordinate() {
		
		refX=0;
		refY=0;
	}

}

