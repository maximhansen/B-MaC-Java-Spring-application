package be.kdg.client.controller.rest.order.xmlExtractor;

import be.kdg.client.controller.dto.OrderClientDto;
import be.kdg.client.controller.dto.ProductDto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderExtractor {
    public static UUID extractAccountId(Document document) {
        NodeList accountList = XmlParser.getElementsByTagName(document, "Account");
        return UUID.fromString(accountList.item(0).getTextContent());
    }

    public static OrderClientDto extractItems(Document document) {
        NodeList itemList = XmlParser.getElementsByTagName(document, "Item");
        OrderClientDto orderClientDto = new OrderClientDto();
        List<ProductDto> items = new ArrayList<>();

        for (int i = 0; i < itemList.getLength(); i++) {
            Element item = (Element) itemList.item(i);
            UUID productNumber = UUID.fromString(item.getAttribute("ProductNumber"));
            int quantity = Integer.parseInt(item.getElementsByTagName("Quantity").item(0).getTextContent());
            String specialInstruction = item.getElementsByTagName("SpecialInstruction").item(0).getTextContent();
            ProductDto productDto = new ProductDto();
            productDto.setQuantity(quantity);
            productDto.setProductNumber(productNumber);
            if(!specialInstruction.isEmpty())
            {
                productDto.setSpecialRequest(specialInstruction);
            }
            items.add(productDto);
        }
        orderClientDto.setProductDtoList(items);
        return orderClientDto;
    }
}
