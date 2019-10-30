package com.ntlts.C868.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemCategoryDAO {
    public ItemCategory getItemCategory(int itemId){
        ItemCategory itemCategory = new ItemCategory();
        ItemDAO itemDAO = new ItemDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        Item item = itemDAO.getItem(itemId);
        Category category = categoryDAO.getCategory(item.getCateogryId());
        itemCategory.setItemId(item.getItemId());
        itemCategory.setItemName(item.getItemName());
        itemCategory.setCategoryId(category.getCategoryId());
        itemCategory.setCategoryName(category.getCategoryName());
        return itemCategory;
    }

    public ObservableList<ItemCategory> getItemCategory() {
        ItemDAO itemDAO = new ItemDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        ObservableList<Item> itemList = itemDAO.getItem();
        ObservableList<ItemCategory> itemCategoryList = FXCollections.observableArrayList();
        for(Item i : itemList) {
            ItemCategory itemCategory = new ItemCategory();
            itemCategory.setItemId(i.getItemId());
            itemCategory.setItemName(i.getItemName());
            itemCategory.setCategoryId(i.getCateogryId());
            itemCategory.setCategoryName(categoryDAO.getCategory(i.getCateogryId()).getCategoryName());
            itemCategoryList.add(itemCategory);
        }
        return itemCategoryList;
    }
}
