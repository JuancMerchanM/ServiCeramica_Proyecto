package Logic;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.ProductOrder;
import Model.Sale;

public class Reports {
    public static Map<String, Object> generateReport(List<Sale> saleList, LocalDate date, String mode) {
        List<Sale> filteredSales = filterSalesByMode(saleList, date, mode);
        return processSales(filteredSales);
    }

    private static List<Sale> filterSalesByMode(List<Sale> saleList, LocalDate date, String mode) {
        switch (mode.toLowerCase()) {
            case "día":
                return saleList.stream()
                        .filter(sale -> sale.getSaleDate().equals(date))
                        .collect(Collectors.toList());

            case "mes":
                return saleList.stream()
                        .filter(sale -> sale.getSaleDate().getYear() == date.getYear() &&
                                sale.getSaleDate().getMonth() == date.getMonth())
                        .collect(Collectors.toList());

            case "año":
                return saleList.stream()
                        .filter(sale -> sale.getSaleDate().getYear() == date.getYear())
                        .collect(Collectors.toList());

            default:
                throw new IllegalArgumentException("Modo no válido: " + mode);
        }
    }

    private static Map<String, Object> processSales(List<Sale> saleList) {
        double totalSold = 0.0;
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Sale sale : saleList) {
            for (ProductOrder order : sale.getProducts()) {
                totalSold += order.getFinalValue();

                String category = order.getCategory();
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + order.getQuantity());
            }
        }

        String bestCategory = categoryCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay ventas");

        String worstCategory = categoryCount.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay ventas");

        Map<String, Object> report = new HashMap<>();
        report.put("TotalSold", totalSold);
        report.put("WorstCategory", worstCategory);
        report.put("BestCategory", bestCategory);

        return report;
    }
}

