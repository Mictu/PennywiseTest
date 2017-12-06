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
		BorderPane root = new BorderPane();
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
		
		
		reg = new Region();
		reg.setPrefHeight(15);
		reg2 = new Region();
		reg2.setPrefHeight(15);
		
		vCenter.getChildren().addAll(hCenter1, hCenter2, hCenter3, reg2, labels, reg, stackPane);
		
		TextArea playerStats = new TextArea();
		playerStats.setEditable(false);
		playerStats.setMaxHeight(100);
		playerStats.setMaxWidth(200);
		playerStats.setId("playerStats");
		
		
		StackPane centerSP = new StackPane();
		centerSP.getChildren().addAll(playerStats, vCenter);
		StackPane.setAlignment(playerStats, Pos.TOP_RIGHT);
		
		root.setCenter(centerSP);
		
		
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

		// SET BOTTOM

		// SET SCENE
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Dominion.css").toExternalForm());
		stage.setScene(scene);

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
	
	
	
	
	ArrayList<Button> enableDisable = new ArrayList<Button>();
	
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
		for(Button b : handCards){
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
		}
		for (Button m : kingdom) {
			m.setEffect(null);
			m.setDisable(false);
			hCenter2.getChildren().add(m);
		}
		for (Button n : treasure) {
			n.setEffect(null);
			n.setDisable(false);
			hCenter3.getChildren().add(n);
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
		}
		for (Button m : kingdom) {
			m.setEffect(shadow);
			m.setDisable(true);
			hCenter2.getChildren().add(m);
		}
		for (Button n : treasure) {
			n.setEffect(shadow);
			n.setDisable(true);
			hCenter3.getChildren().add(n);
		}
	}
	
}// close class

// Written by Patrick