package rejpabook.rejpashop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rejpabook.rejpashop.domain.Item.Item;
import rejpabook.rejpashop.domain.Member;
import rejpabook.rejpashop.domain.Order;
import rejpabook.rejpashop.domain.OrderSearch;
import rejpabook.rejpashop.service.ItemService;
import rejpabook.rejpashop.service.MemberService;
import rejpabook.rejpashop.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {

        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}