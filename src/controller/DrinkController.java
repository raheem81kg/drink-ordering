package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Drink;
import repository.DrinkRepository;

public class DrinkController {
    private final DrinkRepository drinkRepository;

    public DrinkController(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    // Add a new drink
    public void addDrink(String drinkName, boolean isAlcoholic, double cost) {
        Drink drink = new Drink(drinkName, isAlcoholic, cost);
        try {
			drinkRepository.addDrink(drink);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    // Modify an existing drink
    public void modifyDrink(int drinkID, String drinkName, boolean isAlcoholic, double cost) {
        Drink drink = null;
		try {
			drink = drinkRepository.getDrinkById(drinkID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (drink != null) {
            drink.setDrinkName(drinkName);
            drink.setAlcoholic(isAlcoholic);
            drink.setCost(cost);
            try {
				drinkRepository.updateDrink(drink);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    // Remove a drink
    public void removeDrink(int drinkID) {
        try {
			drinkRepository.removeDrink(drinkID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	public List<Drink> getAllDrinks() {
		List<Drink> drinks = new ArrayList<>();
		try {
			drinks = drinkRepository.getAllDrinks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drinks;
	}

	public int getDrinkIdByName(String selectedDrink) {
		
			return drinkRepository.getDrinkBydrinkName(selectedDrink);
	}
}
