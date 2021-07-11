package com.example.arek.projekt;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO
{
    @Insert
    public void AddShop(Shop shop);

    @Query("SELECT * FROM Shops ORDER BY name ASC")
    List<Shop> GetAllShops();

    @Query("SELECT address FROM Shops WHERE id = :id")
    String GetShopAddressByID(int id);

    @Update
    void UpdateShop(Shop shop);

    @Query("UPDATE Shops SET name = :name and address = :address WHERE id = :id")
    public void UpdateShop(int id, String name, String address);

    @Delete
    public void deleteShop(Shop shop);



    @Insert
    public void AddClient(Client client);

    @Query("SELECT * FROM Clients WHERE login Like :login")
    public Client FindClient(String login);

    @Update
    void UpdateClient(Client client);

    @Delete
    void deleteClient (Client client);

    @Query("DELETE FROM Clients")
    public void nukeTable();


    @Insert
    public void AddProduct(Product product);

    @Query("SELECT * FROM Products WHERE id = :id")
    Product GetProductById(int id);

    @Query("SELECT * FROM Products WHERE idShop = :shop_id")
    List<Product> GetAllProducts(int shop_id);

    @Update
    void UpdateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM Products")
    public void nukeProduct();
}
