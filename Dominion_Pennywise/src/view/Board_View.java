package view;

import java.util.ArrayList;

import controllers.Board_Controller;
import controllers.ClientHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_Class.ServiceLocator;
import server.client.Client;
import server_Models.Translator;

public class Board_View {

	public Stage stage;
	Client client;
	
		/* WRITE DYNAMIC HAND
		 * CARDS ARE COVERED IF HOVERED
		 * SET CHAT
		 * GOLDEN BORDER IF U CAN PLAY CARDS
		 * DECK AND DISCARD DECK SHOULD BE SEEN FROM BEHIND
		 */
	
	// Initialize the GUI Content here
	public Button endPhase, bonusMoney, pay;
	public HBox hBoxHand; 
	Board_Controller bcontroller; 
	protected HBox hCenter1, hCenter2, hCenter3;
	
	protected DropShadow shadow = new DropShadow();
	
	protected ArrayList <Button> handCards = new ArrayList<Button>();
	protected ArrayList <String> handFromServer = new ArrayList<String>();
	
	protected ArrayList <Button> treasure = new ArrayList<Button>();
	protected ArrayList <Button> kingdom = new ArrayList<Button>();
	protected ArrayList <Button> victory = new ArrayList<Button>();

	ClientHandler clientHandler; 
	BorderPane root;
	
//	 constructor
	public Board_View(Stage s, Client client) {
		this.client = client;
		CardDesign_View cdV = new CardDesign_View(client);
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		
		this.stage = s;

		// Set up the GUI in here
		stage.setTitle("Dominion");
		// stage.setResizable(false);
		root = new BorderPane();
		root.setId("boardRoot");
		root.setPadding(new Insets(10, 10, 10, 10));
		root.autosize();
		root.layoutBoundsProperty();

		// SET TOP
		// SET CENTER
		// use V- and H- Boxes to add the Cards (Buttons)
		VBox vCenter = new VBox(8);
		vCenter.setAlignment(Pos.CENTER);
		hCenter1 = new HBox(20); // add Victory cards
		hCenter2 = new HBox(20); // add Kingdom-/ Action card
		hCenter3 = new HBox(20); // add Treasure cards

		vCenter.setPadding(new Insets(0, 10, 0, 0));
		hCenter1.setId("boxes");
		hCenter2.setId("boxes");
		hCenter3.setId("boxes");
		
		try {
			
			treasure.add(cdV.getCopperBtn());
			treasure.add(cdV.getSilverBtn());
			treasure.add(cdV.getGoldBtn());
			kingdom.add(cdV.getFunfairBtn());
			kingdom.add(cdV.getLaboratoryBtn());
			kingdom.add(cdV.getMarketBtn());
			kingdom.add(cdV.getSmithBtn());
			kingdom.add(cdV.getVillageBtn());
			kingdom.add(cdV.getWoodcutterBtn());
			victory.add(cdV.getEstateBtn());
			victory.add(cdV.getDuchyBtn());
			victory.add(cdV.getProvinceBtn());
			
			setCardsOnViewEnable();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		endPhase = new Button("Phase beenden");
		endPhase.setId("endBtn");
		bonusMoney = new Button("Bonusgeld: 0");
		bonusMoney.setId("btn");
		pay = new Button("Bezahlen bestštigen");
		pay.setId("btn");
		
		HBox labels = new HBox(20) ;
			Label firstPhase = new Label(t.getString("dominion.board.lbl.actionPhase"));
			firstPhase.setId("phaseLabels");
			Label secondPhase = new Label(t.getString("dominion.board.lbl.buyPhase"));
			secondPhase.setId("phaseLabels");
			Label thirdPhase = new Label(t.getString("dominion.board.lbl.cleanupPhase"));
			thirdPhase.setId("phaseLabels");
			
			Region reg = new Region();
			Region reg2 = new Region();
			reg.setPrefWidth(140);
			reg2.setPrefWidth(140);
			
			labels.setAlignment(Pos.CENTER);
			
		labels.getChildren().addAll(firstPhase, secondPhase, thirdPhase, reg, bonusMoney, pay, reg2, endPhase);
		
		//bindings for the part in between of players hand and cards to buy
		bindingsForContent(firstPhase, labels, 0.98, 0.09);
		bindingsForContent(secondPhase, labels, 0.98, 0.09);
		bindingsForContent(thirdPhase, labels, 0.98, 0.09);
		bindingsForContent(reg, labels, 0.98, 0.2);
		bindingsForContent(bonusMoney, labels, 0.98, 0.09);
		bindingsForContent(pay, labels, 0.98, 0.09);
		bindingsForContent(reg2, labels, 0.98, 0.2);
		bindingsForContent(endPhase, labels, 0.98, 0.09);
		
	//CENTER BOTTOM	
		
		StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(0,10,0,0));
		
		Label hand = new Label(t.getString("dominion.board.lbl.hand"));
		hand.setId("handLabel");
		
		hBoxHand = new HBox(20);
		hBoxHand.setAlignment(Pos.CENTER);
		
		
		// testing the players hand
		
//		hBoxHand.getChildren().addAll(cdV.getWoodcutterBtn(), cdV.getFunfairBtn(), cdV.getFunfairBtn(),
//				cdV.getFunfairBtn(), cdV.getFunfairBtn());

		Button deck = new Button();
		deck.setId("back");
		Button discard = new Button();
		discard.setId("back");
		
		HBox hBottom = new HBox(800);								// Spacing between deck and discard
		hBottom.getChildren().addAll(deck, discard);
		hBottom.setAlignment(Pos.CENTER);
		
		stackPane.getChildren().addAll(hand,hBottom,hBoxHand);
		root.setBottom(stackPane);
		
		bindingsForContent(hand, stackPane, 0.98, 1);
		bindingsForContent(hBottom, stackPane, 0.98, 1);
		bindingsForContent(hBoxHand, stackPane, 0.98, 1);
		
		reg = new Region();
		reg2 = new Region();
		
		vCenter.getChildren().addAll(hCenter1, hCenter2, hCenter3, reg2, labels, reg, stackPane);
		
		//bindings for Center content
		bindingsForContent(hCenter1, vCenter, 0.2, 0.65);
		bindingsForContent(hCenter2, vCenter, 0.2, 0.65);
		bindingsForContent(hCenter3, vCenter, 0.2, 0.65);
		bindingsForContent(reg, vCenter, 0.01, 0.65);
		bindingsForContent(labels, vCenter, 0.05, 0.65);
		bindingsForContent(reg2, vCenter, 0.01, 0.65);
		bindingsForContent(stackPane, vCenter, 0.2, 0.65);
		
		TextArea playerStats = new TextArea();
		playerStats.setEditable(false);
		playerStats.setMaxHeight(100);
		playerStats.setMaxWidth(200);
		playerStats.setId("playerStats");
		
		StackPane centerSP = new StackPane();
		centerSP.getChildren().addAll(playerStats, vCenter);
		StackPane.setAlignment(playerStats, Pos.TOP_RIGHT);
		root.setCenter(centerSP);
		
		bindingsForContent(playerStats, centerSP, 0.18, 0.18);
		bindingsForContent(vCenter, centerSP, 1, 1);
		bindingsForContent(centerSP, root, 0.98, 0.8);

	//SET RIGHT
		VBox vRight = new VBox (10);
		
		HBox chatInput = new HBox(5);
		TextField chatText = new TextField();
		Button send = new Button();
		send.setId("sendButton");
		send.setText(t.getString("dominion.lobby.btn.send"));
		
		chatInput.getChildren().addAll(chatText, send);
		chatInput.setAlignment(Pos.CENTER);
		HBox.setHgrow(chatText, Priority.ALWAYS);
		
		TextArea logger = new TextArea();
		logger.setEditable(false);
		TextArea chat = new TextArea();
		chat.setEditable(false);
		
		reg = new Region();
		reg.setPrefHeight(20);
		
		vRight.getChildren().addAll(logger, reg, chat, chatInput);
		
		root.setRight(vRight);
		
		// bindings (space) for the content
		bindingsForContent(chatText, chatInput, 1, 0.622);
		bindingsForContent(send, chatInput, 1, 0.35);
		
		bindingsForContent(logger, vRight, 0.42, 1);
		bindingsForContent(reg, vRight, 0.05, 1);
		bindingsForContent(chat, vRight, 0.42, 1);
		bindingsForContent(chatInput, vRight, 0.04, 1);

		bindingsForContent(vRight, root, 0.98, 0.18);
		
		
		// SET BOTTOM

		// SET SCENE
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Dominion.css").toExternalForm());
		stage.setScene(scene);

		setStageBindings(root, stage, 1, 1);
	} // Close the Constructor

	public void start() {
		stage.setFullScreen(true);
		stage.show();
	}
	
	public Stage getStage(){
		return this.stage; 
	}
	
	public void stop () {
		this.stage.hide();
	}
	
	public void setHand(String card) {

		if (!card.equals("end")) {
			handFromServer.add(card);
		}
		if (card.equals("end")) {
			this.hBoxHand.getChildren().clear();
			while (handCards.size() < handFromServer.size()) {
				Button b = new Button();
				handCards.add(b);
			}
			setCards();
			for (int i = 0; i < handFromServer.size(); i++) {
				handCards.get(i).setId(handFromServer.get(i));
				this.hBoxHand.getChildren().add(handCards.get(i));
			}
		}
		for (Button b : handCards) {
			b.setDisable(false);
		}
		handCards.clear();
	}

	public void setCards() {
		String phase = clientHandler.getPhase();
		if (phase.equals("buy")) {
			setCardsOnViewEnable();
			for (Button b : handCards) {
				if (!b.getId().equals("copper") || !b.getId().equals("silver") || !b.getId().equals("gold")) {
					b.setDisable(true);
				} else {
					b.setDisable(false);
				}
			}
		}
		if (phase.equals("action")) {
			setCardsOnViewDisable();
			for (Button b : handCards) {
				if (!b.getId().equals("smith") || !b.getId().equals("market") || !b.getId().equals("laboratory")
						|| !b.getId().equals("funfair") || !b.getId().equals("woodcutter")
						|| !b.getId().equals("village")) {
					b.setDisable(true);
				} else {
					b.setDisable(false);
				}
			}
		}
	}

	// enables buttons while player is able to chose which card he wants to buy
	public void setCardsOnViewEnable() {
		hCenter1.getChildren().clear();
		hCenter2.getChildren().clear();
		hCenter3.getChildren().clear();
		for (Button l : victory) {
			l.setEffect(null);
			l.setDisable(false);
			hCenter1.getChildren().add(l);
			bindingsForContent(l, hCenter1, 1, 0.13725);
		}
		for (Button m : kingdom) {
			m.setEffect(null);
			m.setDisable(false);
			hCenter2.getChildren().add(m);
			bindingsForContent(m, hCenter2, 1, 0.13725);
		}
		for (Button n : treasure) {
			n.setEffect(null);
			n.setDisable(false);
			hCenter3.getChildren().add(n);
			bindingsForContent(n, hCenter3, 1, 0.13725);
		}
	}

	// disables buttons while player is in actionphase
	public void setCardsOnViewDisable() {
		hCenter1.getChildren().clear();
		hCenter2.getChildren().clear();
		hCenter3.getChildren().clear();
		for (Button l : victory) {
			l.setEffect(shadow);
			l.setDisable(true);
			hCenter1.getChildren().add(l);
			bindingsForContent(l, hCenter1, 1, 0.13725);
		}
		for (Button m : kingdom) {
			m.setEffect(shadow);
			m.setDisable(true);
			hCenter2.getChildren().add(m);
			bindingsForContent(m, hCenter2, 1, 0.13725);
		}
		for (Button n : treasure) {
			n.setEffect(shadow);
			n.setDisable(true);
			hCenter3.getChildren().add(n);
			bindingsForContent(n, hCenter3, 1, 0.13725);
		}
	}
	
	// Resize BoardView content automatically via bindings
	public void bindingsForContent(Region child, Region parent, double heightMultiply, double widthMultiply) {
		child.maxHeightProperty().bind(parent.heightProperty().multiply(heightMultiply));
		child.maxWidthProperty().bind(parent.widthProperty().multiply(widthMultiply));
		child.minHeightProperty().bind(parent.heightProperty().multiply(heightMultiply));
		child.minWidthProperty().bind(parent.widthProperty().multiply(widthMultiply));
	}
	protected void setStageBindings(Region child, Stage stage2, double heightMultiply, double widthMultiply){
		child.maxHeightProperty().bind(stage2.heightProperty().multiply(heightMultiply));
		child.maxWidthProperty().bind(stage2.widthProperty().multiply(widthMultiply));
		child.minHeightProperty().bind(stage2.heightProperty().multiply(heightMultiply));
		child.minWidthProperty().bind(stage2.widthProperty().multiply(widthMultiply));
	}
	
}// close class

// Written by Patrick