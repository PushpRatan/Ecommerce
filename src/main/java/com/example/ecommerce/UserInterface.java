package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane Login;
    HBox header, footer;
    Customer customerLoggedIn;
    ProductList productList = new ProductList();
    Label UserWelcome;
    VBox productPage, body, cartPage;
    ObservableList<Product> cartData = FXCollections.observableArrayList();

    Button SignInButton = new Button("Sign In");
    Button placeOrderButton = new Button("Place Order");
    Button back = new Button("Back");
    Button addToCartButton;
    Button ordersButton = new Button("Orders");
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
//        root.getChildren().add(login);
        body = new VBox();
        root.setTop(header);
        root.setCenter(Login);
        root.setBottom(footer);
        productPage = productList.getData();
//        root.setCenter(productPage);
        root.setCenter(body);
        body.getChildren().add(productPage);
        body.setPadding(new Insets(25));
        body.setAlignment(Pos.CENTER);
        root.setPrefSize(800, 500);
        return root;
    }
    public UserInterface(){
        creatingLoginPage();
        creatingHeaderBar();
        creatingFooterBar();
    }
    private void creatingLoginPage(){
        Text userName = new Text("Username");
        Text password = new Text("Password");
        TextField userNameInput = new TextField();
        userNameInput.setPromptText("UserName");
        userNameInput.setText("Pushp Ratan");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Pa$$w0rd");
        passwordInput.setText("Rat9835@an");
        Label messageLabel = new Label("");
        Button loginButton =new Button("login");

        Login = new GridPane();
        Login.setAlignment(Pos.CENTER);
        Login.setHgap(10);
        Login.setVgap(10);
        Login.add(userName, 0, 0);
        Login.add(userNameInput, 1, 0);
        Login.add(password, 0, 1);
        Login.add(passwordInput, 1, 1);
        Login.add(messageLabel, 0, 2);
        Login.add(loginButton, 1, 2);
        Login.setTranslateX(-20);
        Login.setTranslateY(-30);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userNameInput.getText();
                String password = passwordInput.getText();
                Login login = new Login();
                customerLoggedIn = login.customerLogin(name, password);
                if(customerLoggedIn!=null){
                    UserWelcome = new Label("Hi "+customerLoggedIn.getName());
                    header.getChildren().add(UserWelcome);
                }else{
                    header.getChildren().add(SignInButton);
                    SignInButton.setText("SignIn");
                }
                userNameInput.clear();
                passwordInput.clear();
                body.getChildren().clear();
                body.getChildren().add(productPage);
                if(!footer.isVisible()){
                    footer.setVisible(true);;
                }
            }
        });
    }

    private void creatingHeaderBar(){
        Button homeButton = new Button();
        Image image = new Image("D:\\Projects\\Acciojob\\Ecommerce\\src\\home.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        homeButton.setGraphic(imageView);
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        Button searchButton = new Button("Search");
        Button cartButton = new Button("Cart");
        searchBar.setPrefWidth(300);
        header = new HBox();
        header.getChildren().addAll(homeButton, searchBar, searchButton, SignInButton, cartButton, ordersButton);
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                if (customerLoggedIn==null && !header.getChildren().contains(SignInButton)){
                    header.getChildren().remove(cartButton);
                    header.getChildren().addAll(SignInButton, cartButton);
                }
                footer.setVisible(true);
            }
        });

        SignInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(Login);
                Login.setPadding(new Insets(25));
                header.getChildren().remove(SignInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                footer.getChildren().remove(addToCartButton);
                cartPage = productList.getCartData(cartData);
                body.getChildren().clear();
                body.getChildren().add(cartPage);
                body.getChildren().addAll(placeOrderButton, back);
                body.setSpacing(10);
                footer.setVisible(false);
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        body.getChildren().clear();
                        body.getChildren().add(productPage);
                        footer.setVisible(true);
                    }
                });
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cartData==null) {
                    showDialog("Please add Items in the cart.");
                    return;
                }
                if(customerLoggedIn == null){
                    showDialog("Please login first.");
                    return;
                }
                int count = Order.placeCartOrder(customerLoggedIn, cartData);
                if(count!=0) {
                    showDialog("Order for "+count+" Items Placed Successfully!");
                }else{
                    showDialog("Order Failed!");
                }
            }
        });

        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                
            }
        });

    }
    private void creatingFooterBar(){
        Button buyNowButton = new Button("Buy Now");
        addToCartButton = new Button("Add to Cart");
        footer = new HBox();
        footer.getChildren().addAll(buyNowButton, addToCartButton);
        footer.setSpacing(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product==null) {
                    showDialog("Please select an Item.");
                    return;
                }
                if(customerLoggedIn == null){
                    showDialog("Please login first.");
                    return;
                }
                if(Order.placeOrder(customerLoggedIn, product)) {
                    showDialog("Order Placed Successfully!");
                }else{
                    showDialog("Order Failed!");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    showDialog("Please select an Item");
                    return;
                }
                cartData.add(product);
                showDialog("Item added to the Cart");
            }
        });
    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
