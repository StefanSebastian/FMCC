package com.mfcc.beer_shop.persistence;

import com.mfcc.beer_shop.model.Beer;
import com.mfcc.beer_shop.model.Stock;
import com.mfcc.twopl.exceptions.TwoPLException;
import com.mfcc.twopl.model.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class BeerPersistence {

    private Logger logger = LoggerFactory.getLogger(BeerPersistence.class);

    @Autowired
    private JDBCUtils jdbcUtils;

    public void storeBeerQueries(Operation storeBeerOp, Operation storeStockOp, Beer beer, Stock stock) {
        storeBeerOp.setAction(() -> {
            try {
                Connection beerDbConn = jdbcUtils.getConnectionToBeerDb();
                String sql = "insert into beers(name, style, description, producer) values(?, ?, ?, ?)";
                PreparedStatement ps = beerDbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, beer.getName());
                ps.setString(2, beer.getStyle());
                ps.setString(3, beer.getDescription());
                ps.setString(4, beer.getProducer());

                int affectedRows = ps.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        beer.setId(generatedKeys.getLong(1));
                        stock.setBeerId(beer.getId());
                    }
                    else {
                        throw new SQLException("Creating beer failed, no ID obtained.");
                    }
                }
            } catch (SQLException e){
                logger.debug(e.getMessage());
                throw new TwoPLException(e.getMessage());
            }
        });
        storeBeerOp.setRollback(() -> {
            try {
                Connection beerDbConn = jdbcUtils.getConnectionToBeerDb();
                String sql = "delete from beers where id = ?";
                PreparedStatement ps = beerDbConn.prepareStatement(sql);
                ps.setLong(1, beer.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.debug(e.getMessage());
            }
        });
        storeStockOp.setAction(() -> {
            try {
                Connection beerDbConn = jdbcUtils.getConnectionToBeerDb();
                String sql = "insert into stocks(beer_id, available, price) values(?, ?, ?)";
                PreparedStatement ps = beerDbConn.prepareStatement(sql);
                ps.setLong(1, stock.getBeerId());
                ps.setInt(2, stock.getAvailable());
                ps.setFloat(3, stock.getPrice());
                
                int affectedRows = ps.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating stock failed, no rows affected.");
                }
            } catch (SQLException e){
                logger.debug(e.getMessage());
                throw new TwoPLException(e.getMessage());
            }
        });
        storeStockOp.setRollback(() -> {
            try {
                Connection beerDbConn = jdbcUtils.getConnectionToBeerDb();
                String sql = "delete from stocks where beer_id = ?";
                PreparedStatement ps = beerDbConn.prepareStatement(sql);
                ps.setLong(1, stock.getBeerId());
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.debug(e.getMessage());
            }
        });
    }
}
