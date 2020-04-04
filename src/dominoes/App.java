package dominoes;

//application and scene
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;


/**
 *
 */
public class App extends Application {

	protected static final double SCENE_WIDTH = 1300;
	protected static final double SCENE_HEIGHT = 1000;

	public BorderPane root;
	public TopBox topBox;
	public SideBox leftBox, rightBox;
	public BotBox botBox;
	public CenterPane centerPane;
	public Game game;

	private Scene scene;
	//private Stage stage;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage){
		root = new BorderPane();

		scene = new Scene(root);
		String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);
		//this.stage = stage;

		topBox = new TopBox();
		botBox = new BotBox(this);
		leftBox = new SideBox();
		rightBox = new SideBox();

		root.setLeft(leftBox);
		root.setRight(rightBox);
		root.setTop(topBox);
		root.setBottom(botBox);

		centerPane = new CenterPane();
		root.setCenter(centerPane);

		stage.setScene(scene);
		stage.setWidth(SCENE_WIDTH);
		stage.setHeight(SCENE_HEIGHT);
		stage.setResizable(false);
		stage.setTitle("Dominoes!");
		stage.show();

		game = new Game(this);
		game.play();
	}//start 

}//LineApp
