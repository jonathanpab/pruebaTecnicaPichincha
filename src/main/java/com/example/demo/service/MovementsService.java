package com.example.demo.service;

import com.example.demo.entity.AccountClient;
import com.example.demo.entity.Movements;
import com.example.demo.repository.AccountClientRepository;
import com.example.demo.repository.MovementsRepository;
import com.example.demo.structure.MovementsStructure;
import com.example.demo.structure.ReportMovementsStructure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MovementsService {

    private MovementsRepository movementsRepository;
    private AccountClientRepository accountClientRepository;

    @Value("${daily_limit}")
    private long dailyLimit;

    public MovementsService(MovementsRepository movementsRepository, AccountClientRepository accountClientRepository) {
        super();
        this.movementsRepository = movementsRepository;
        this.accountClientRepository = accountClientRepository;
    }

    public String saveMovements(MovementsStructure movementsStructure) {
        Date startDate = new Date();
        startDate.setTime(0);
        Date finalDate = new Date();
        finalDate.setHours(23);
        finalDate.setMinutes(59);
        finalDate.setSeconds(59);
        List<Movements> daily_movements = movementsRepository.findByAccount(movementsStructure.getNumero_cuenta(), startDate, finalDate);
        final float[] total = {0};
        daily_movements.forEach((movement) -> {

            if (movement.getTipo_movimiento().equals('D'))
                total[0] = total[0] + movement.getValor();
        });
        AccountClient accountClient = accountClientRepository.findByAccountNumber(movementsStructure.getNumero_cuenta());

        if (Float.compare(accountClient.getSaldo_inicial(), movementsStructure.getSaldo_inicial()) != 0) {
            return "Saldo inicial no coincide con el saldo de la cuenta" + accountClient.getSaldo_inicial() + " - " + movementsStructure.getSaldo_inicial();
        } else if (movementsStructure.getSaldo_inicial() < movementsStructure.getMovimiento() && movementsStructure.getTipo().equals("retiro")) {
            return "Saldo no disponible";
        } else if (Float.compare(total[0] + movementsStructure.getMovimiento(), dailyLimit) > 0) {
            return "Cupo diario Excedido";
        } else {
            float saldo = movementsStructure.getTipo().equals("retiro") ?
                    movementsStructure.getSaldo_inicial() - movementsStructure.getMovimiento() :
                    movementsStructure.getSaldo_inicial() + movementsStructure.getMovimiento();
            Movements movements = new Movements();
            movements.setValor(movementsStructure.getMovimiento());
            movements.setFecha(new Timestamp(new Date().getTime()));
            movements.setTipo_movimiento(movementsStructure.getTipo().equals("retiro") ? 'D' : 'C');
            movements.setSaldo(saldo);
            accountClient.setSaldo_inicial(saldo);
            movements.setCuenta(accountClient);
            movementsRepository.save(movements);
            accountClientRepository.save(accountClient);
            return "Movimiento (" + movementsStructure.getTipo() + ") realizado";
        }
    }

    public List<Movements> getAllMovements() {
        return movementsRepository.findAll();
    }

    public JSONArray getMovementsByUser(ReportMovementsStructure reportMovementsStructure) {
        JSONArray report = new JSONArray();

        Date startDate = null;
        Date finalDate = null;
        List<Movements> movements = null;
        try {
            startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(reportMovementsStructure.getFecha_desde());
            finalDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(reportMovementsStructure.getFecha_hasta());
            movements = movementsRepository.findByUser(reportMovementsStructure.getUsuario(),
                    startDate, finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        movements.forEach((movement) -> {
            JSONObject movementObject = new JSONObject();

            movementObject.put("Fecha", movement.getFecha());
            movementObject.put("Cliente", movement.getCuenta().getClient().getPerson().getNombre());
            movementObject.put("Numero Cuenta", movement.getCuenta().getNumero_cuenta());
            movementObject.put("Tipo", movement.getCuenta().getTipo());
            movementObject.put("Saldo Inicial", movement.getSaldo());
            movementObject.put("Estado", movement.getCuenta().getEstado());
            movementObject.put("Movimiento", movement.getTipo_movimiento());
            movementObject.put("Saldo Disponible", movement.getCuenta().getSaldo_inicial());


            report.add(movementObject);
        });

        return report;
    }

    public Movements updateMovements(Movements movements, long id) {

        // we need to check whether movements with given id is exist in DB or not
        Movements existingMovements = movementsRepository.findById(id).orElseThrow(() ->
                new Error("Movement not found with id " + id));

        existingMovements.setTipo_movimiento(movements.getTipo_movimiento());
        existingMovements.setValor(movements.getValor());
        existingMovements.setSaldo(movements.getSaldo());
        // save existing movements to DB
        movementsRepository.save(existingMovements);
        return existingMovements;
    }

    public void deleteMovements(long id) {

        // check whether a movements exist in a DB or not
        movementsRepository.findById(id).orElseThrow(() ->
                new Error("Movements not found with id " + id));
        movementsRepository.deleteById(id);
    }

}
