package com.unibite.unibit_backend.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.unibite.unibit_backend.dto.BillResponse;
import com.unibite.unibit_backend.entity.OrderItem;
import com.unibite.unibit_backend.entity.Orders;
import com.unibite.unibit_backend.repository.OrderItemRepository;

import com.unibite.unibit_backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final OrderItemRepository orderItemRepository;
//    private final BillResponse billResponse;
    private final OrderRepository orderRepository;

    public byte[] generateBill(Orders order){

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // 🔹 TITLE
            document.add(new Paragraph("UNIBITE RESTAURANT")
                    .setFontSize(20));

            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("Date: " + LocalDateTime.now()));
            document.add(new Paragraph("Status: " + order.getStatus()));
            document.add(new Paragraph("\n"));

            // 🔹 TABLE
            float[] columnWidths = {200, 100, 100};
            Table table = new Table(columnWidths);

            // Header
            table.addHeaderCell(new Cell().add(new Paragraph("Item")));
            table.addHeaderCell(new Cell().add(new Paragraph("Qty")));
            table.addHeaderCell(new Cell().add(new Paragraph("Price")));

            // Fetch items
            List<OrderItem> items = orderItemRepository.findByOrders(order);

            for(OrderItem item : items){
                table.addCell(new Cell().add(new Paragraph(item.getFoodItem().getName())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
                table.addCell(new Cell().add(new Paragraph("₹" + item.getPrice())));
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            // 🔹 TOTAL
            document.add(new Paragraph("Total Amount: ₹" + order.getTotalPrice())
                    .setFontSize(14));

            document.add(new Paragraph("\nThank you for your order!"));

            document.close();

        }catch(Exception e){
            throw new RuntimeException("Error generating bill");
        }

        return out.toByteArray();
    }
    public BillResponse getBill(Long orderId){

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrders(order);

        List<BillResponse.Item> billItems = new ArrayList<>();

        for(OrderItem item : items){
            billItems.add(new BillResponse.Item(
                    item.getFoodItem().getName(),
                    item.getQuantity(),
                    item.getPrice()
            ));
        }

        return BillResponse.builder()
                .orderId(order.getId())
                .UserEmail(order.getUserEmail())
                .items(billItems)
                .total(order.getTotalPrice())
                .paymentStatus("PENDING")
                .utr("N/A")
                .build();
    }
}