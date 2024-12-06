package Logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.ProductOrder;
import Model.Sale;

public class Reports {
    public static Map<String, Object> generateReport(List<Sale> saleList) {
        double totalSold = 0.0;
        Map<String, Integer> categoryCount = new HashMap<>();
        int productCount = 0;

        for (Sale sale : saleList) {
            for (ProductOrder order : sale.getProducts()) {
                totalSold += order.getFinalValue();

                productCount += order.getQuantity();

                String category = order.getCategory();
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + order.getQuantity());
            }
        }

        String bestCategory = categoryCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No categories");

        Map<String, Object> report = new HashMap<>();
        report.put("TotalSold", totalSold);
        report.put("ProductQuantities", productCount);
        report.put("BestCategory", bestCategory);

        return report;
    }
}
