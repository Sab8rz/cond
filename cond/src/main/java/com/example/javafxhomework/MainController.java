package com.example.javafxhomework;

import com.example.javafxhomework.DAO.*;
import com.example.javafxhomework.models.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class MainController {
    @FXML private ComboBox<Pastry> choosePastry, choosePastryIngredient;
    @FXML private ComboBox<Seller> chooseSeller;
    @FXML private TableView<Deal> dealTableView;
    @FXML private TextField pastryCaloriesField, pastryPriceField, pastryTitleFIeld, sellerFullNameField, sellerSalaryField, ingredientQuantityField;
    @FXML BarChart pastryBarChart, ingredientBarChart, sellerBarChart;
    @FXML private TableView<Pastry> pastryTableView;
    @FXML private TableView<Seller> sellerTableView;
    @FXML private TableView<Ingredient> ingredientTableView;

    private SellerDAO sellerDAO = new SellerDAO();
    private PastryDAO pastryDAO = new PastryDAO();
    private DealDAO dealDAO = new DealDAO();
    private IngredientDAO ingredientDAO = new IngredientDAO();

    @FXML void addDeal(ActionEvent event) {
        try {
            Deal deal = new Deal(chooseSeller.getValue().getId(), choosePastry.getValue().getId());
            dealDAO.save(deal);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    @FXML void addIngredient(ActionEvent event) {
        try {
            Ingredient ingredient = new Ingredient(choosePastryIngredient.getValue().getId(), Long.parseLong(ingredientQuantityField.getText()));
            ingredientDAO.save(ingredient);
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            analyzeIngredients();
        }
    }

    @FXML void addPastry(ActionEvent event) {
        try {
            Pastry pastry = new Pastry(pastryTitleFIeld.getText(), Long.parseLong(pastryCaloriesField.getText()), Double.parseDouble(pastryPriceField.getText()));
            pastryDAO.save(pastry);
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            analyzePastries();
        }
    }

    @FXML void addSeller(ActionEvent event) {
        try {
            Seller seller = new Seller(sellerFullNameField.getText(), Long.parseLong(sellerSalaryField.getText()));
            sellerDAO.save(seller);
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            analyzeSellers();
        }
    }

    @FXML void deleteDeal(ActionEvent event) {
        dealDAO.remove(dealTableView.getSelectionModel().getSelectedItem());
    }

    @FXML void deleteIngredient(ActionEvent event) {
        ingredientDAO.remove(ingredientTableView.getSelectionModel().getSelectedItem());
        analyzeIngredients();
    }

    @FXML void deletePastry(ActionEvent event) {
        pastryDAO.remove(pastryTableView.getSelectionModel().getSelectedItem());
        analyzePastries();
    }

    @FXML void deleteSeller(ActionEvent event) {
        sellerDAO.remove(sellerTableView.getSelectionModel().getSelectedItem());
        analyzeSellers();
    }

    @FXML void editSeller(ActionEvent event) {
        Seller selectedSeller = sellerTableView.getSelectionModel().getSelectedItem();
        if (selectedSeller == null) {
            return; // Если продавец не выбран, ничего не делаем
        }

        TextInputDialog dialog = new TextInputDialog(selectedSeller.getFullName());
        dialog.setTitle("Редактирование продавца");
        dialog.setHeaderText("Введите новые данные для продавца:");
        dialog.setContentText("Имя и зарплата через пробел:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] parts = result.get().split(" ");
            if (parts.length != 2) {
                return; // Если данные введены некорректно, ничего не делаем
            }

            try {
                Long salary = Long.parseLong(parts[1]);
                selectedSeller.setFullName(parts[0]);
                selectedSeller.setSalary(salary);
                sellerDAO.update(selectedSeller);
            } catch (NumberFormatException e) {
                return; // Если зарплата введена некорректно, ничего не делаем
            }
            analyzeSellers();
            sellerTableView.refresh();
        }
    }

    @FXML void editPastry(ActionEvent event) {
        Pastry selectedPastry = pastryTableView.getSelectionModel().getSelectedItem();
        if (selectedPastry == null) {
            return; // Если выпечка не выбрана, ничего не делаем
        }

        TextInputDialog dialog = new TextInputDialog(selectedPastry.getTitle());
        dialog.setTitle("Редактирование выпечки");
        dialog.setHeaderText("Введите новые данные для выпечки:");
        dialog.setContentText("Название, калории и цена через пробел:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] parts = result.get().split(" ");
            if (parts.length != 3) {
                return; // Если данные введены некорректно, ничего не делаем
            }

            try {
                int calories = Integer.parseInt(parts[1]);
                double price = Double.parseDouble(parts[2]);
                selectedPastry.setTitle(parts[0]);
                selectedPastry.setCalories(calories);
                selectedPastry.setPrice(price);
                pastryDAO.update(selectedPastry);
            } catch (NumberFormatException e) {
                return; // Если калории или цена введены некорректно, ничего не делаем
            }
            analyzePastries();
            pastryTableView.refresh();
        }
    }

    @FXML void editIngredient(ActionEvent event) {
        Ingredient selectedIngredient = ingredientTableView.getSelectionModel().getSelectedItem();
        if (selectedIngredient == null) {
            return; // Если ингредиент не выбран, ничего не делаем
        }

        TextInputDialog dialog = new TextInputDialog(selectedIngredient.getPastry().getTitle());
        dialog.setTitle("Редактирование ингредиента");
        dialog.setHeaderText("Введите новые данные для ингредиента:");
        dialog.setContentText("Название и количество через пробел:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] parts = result.get().split(" ");
            if (parts.length != 2) {
                return; // Если данные введены некорректно, ничего не делаем
            }

            try {
                long quantity = Long.parseLong(parts[1]);
                selectedIngredient.getPastry().setTitle(parts[0]);
                selectedIngredient.setQuantity(quantity);
                ingredientDAO.update(selectedIngredient);
            } catch (NumberFormatException e) {
                return; // Если количество введено некорректно, ничего не делаем
            }
            analyzeIngredients();
            ingredientTableView.refresh();
        }
    }


    @FXML void initialize() {
        initializeSellerTable();
        initializePastryTable();
        initializeDealTable();
        initializeIngredientTable();
        analyzeSellers();
        analyzePastries();
        analyzeIngredients();
        choosePastry.setItems(pastryDAO.observablePastryList);
        choosePastryIngredient.setItems(pastryDAO.observablePastryList);
        chooseSeller.setItems(sellerDAO.observableSellerList);
    }

    private void initializeDealTable() {
        TableColumn<Deal, String> dealSellerColumn = new TableColumn<>("Продавец");
        dealSellerColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSeller().getFullName()));

        TableColumn<Deal, String> dealPastryColumn = new TableColumn<>("Выпечка");
        dealPastryColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPastry().getTitle()));

        TableColumn<Deal, Double> dealPriceColumn = new TableColumn<>("Цена");
        dealPriceColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPastry().getPrice()).asObject());

        dealTableView.getColumns().setAll(dealSellerColumn, dealPastryColumn, dealPriceColumn);
        dealTableView.setItems(dealDAO.observableDealList);
    }

    private void initializeIngredientTable() {

        TableColumn<Ingredient, String> ingredientTitleColumn = new TableColumn<>("Название");
        ingredientTitleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPastry().getTitle()));

        TableColumn<Ingredient, Long> ingredientQuantityColumn = new TableColumn<>("Количество");
        ingredientQuantityColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getQuantity()).asObject());

        ingredientTableView.getColumns().setAll(ingredientTitleColumn, ingredientQuantityColumn);
        ingredientTableView.setItems(ingredientDAO.observableIngredientList);
    }

    private void initializePastryTable() {
        TableColumn<Pastry, Integer> pastryIdColumn = new TableColumn<>("ID");
        pastryIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        TableColumn<Pastry, String> pastryTitleColumn = new TableColumn<>("Название");
        pastryTitleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));

        TableColumn<Pastry, Long> pastryCaloriesColumn = new TableColumn<>("Калории");
        pastryCaloriesColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getCalories()).asObject());

        TableColumn<Pastry, Double> pastryPriceColumn = new TableColumn<>("Цена");
        pastryPriceColumn.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPrice()).asObject());
        pastryPriceColumn.setCellFactory(cell -> new TableCell<Pastry, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        pastryTableView.getColumns().setAll(pastryIdColumn, pastryTitleColumn, pastryCaloriesColumn, pastryPriceColumn);
        pastryTableView.setItems(pastryDAO.observablePastryList);
    }

    private void initializeSellerTable() {
        TableColumn<Seller, Integer> sellerIdColumn = new TableColumn<>("ID");
        sellerIdColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

        TableColumn<Seller, String> sellerFullNameColumn = new TableColumn<>("Продавец");
        sellerFullNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFullName()));

        TableColumn<Seller, Long> sellerSalaryColumn = new TableColumn<>("Зарплата");
        sellerSalaryColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getSalary()).asObject());

        sellerTableView.getColumns().setAll(sellerIdColumn, sellerFullNameColumn, sellerSalaryColumn);
        sellerTableView.setItems(sellerDAO.observableSellerList);
    }

    public void analyzeSellers() {
        sellerBarChart.setData(FXCollections.observableArrayList(countSalaries()));
        sellerBarChart.setLegendVisible(false); // убрать легенду
    }
    public List<XYChart.Series<String, Double>> countSalaries() {
        List<XYChart.Series<String, Double>> result = new ArrayList<>();
        List<Seller> sellers = sellerDAO.observableSellerList.stream().toList();

        double totalSalary = sellers.stream()
                .mapToDouble(Seller::getSalary)
                .sum();

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        sellers.forEach(seller -> {
            double percentage = seller.getSalary() / totalSalary;
            String label = String.format("Продавец\nID-%d", seller.getId());
            series.getData().add(new XYChart.Data<>(label, percentage));
        });
        result.add(series);
        return result;
    }

    public void analyzePastries() {
        pastryBarChart.setData(countPastryPrices());
        pastryBarChart.setLegendVisible(false); // убрать легенду
    }
    public ObservableList<XYChart.Series<String, Double>> countPastryPrices() {
        ObservableList<XYChart.Series<String, Double>> result = FXCollections.observableArrayList();
        List<Pastry> pastries = pastryDAO.observablePastryList.stream().toList();
        // Собираем Map, где ключом является название изделия в нижнем регистре, а значением - сумма цен для этого изделия
        Map<String, Double> priceSumMap = pastries.stream()
                .collect(Collectors.toMap(pastry -> pastry.getTitle().toLowerCase(), Pastry::getPrice, Double::sum));

        double total = priceSumMap.values().stream().mapToDouble(Double::doubleValue).sum();

        // Используем Map для вычисления долей каждого изделия в общей стоимости
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        priceSumMap.forEach((name, price) -> {
            double value = price / total;
            String label = name;
            series.getData().add(new XYChart.Data<>(label, value));
        });
        result.add(series);
        return result;
    }

    public void analyzeIngredients() {
        ingredientBarChart.getData().setAll(countQuantities());
        ingredientBarChart.setLegendVisible(false); // убрать легенду
    }
    public ObservableList<XYChart.Series<String, Double>> countQuantities() {
        ObservableList<XYChart.Series<String, Double>> result = FXCollections.observableArrayList();
        List<Ingredient> ingredients = ingredientDAO.observableIngredientList.stream().toList();
        Map<String, Long> quantities = new HashMap<>();

        ingredients.forEach(ingredient -> {
            String title = ingredient.getPastry().getTitle().toLowerCase();
            if (quantities.containsKey(title)) {
                quantities.put(title, quantities.get(title) + ingredient.getQuantity());
            } else {
                quantities.put(title, ingredient.getQuantity());
            }
        });

        double totalQuantity = quantities.values().stream().mapToDouble(Long::doubleValue).sum();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        quantities.forEach((title, quantity) -> {
            double percentage = quantity / totalQuantity;
            String label = String.format("%s", title);
            series.getData().add(new XYChart.Data<>(label, percentage));
        });
        result.add(series);
        return result;
    }
}