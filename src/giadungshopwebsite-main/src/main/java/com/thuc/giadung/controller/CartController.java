package com.thuc.giadung.controller;

import com.thuc.giadung.dto.AddToCartRequest;
import com.thuc.giadung.dto.CartItemDTO;
import com.thuc.giadung.controller.common.BaseController;
import com.thuc.giadung.entity.User;
import com.thuc.giadung.service.ProductService;
import com.thuc.giadung.dto.CartDTO;
import com.thuc.giadung.dto.OrderPerson;
import com.thuc.giadung.entity.Product;
import com.thuc.giadung.service.CartService;
import com.thuc.giadung.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController extends BaseController {

    private final HttpSession session;
    private final OrderService orderService;
    private CartService cartService;
    private ProductService productService;

    @GetMapping
    public String getCartPage(Model model) {
        CartDTO cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        double totalCart = cart.calculateTotalAmount();
        model.addAttribute("totalCart", totalCart);
        return "user/cart";
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest request) {

        if (getCurrentUser() != null) {
            Long productId = request.getProductId();
            Integer quantity = request.getQuantity();
            Product existingProduct = productService.getProductById(productId);

            if(quantity > existingProduct.getQty() || existingProduct.getQty() == 0){
                return ResponseEntity.ok("error");
            }

            CartItemDTO addedItem = new CartItemDTO();
            addedItem.setQuantity(quantity);
            addedItem.setProductId(productId);
            addedItem.setTitle(existingProduct.getTitle());
            if(existingProduct.getSalePrice() == null || existingProduct.getSalePrice() <= 0) {
                addedItem.setPrice(existingProduct.getOriginalPrice());
            } else {
                addedItem.setPrice(existingProduct.getSalePrice());
            }
            addedItem.setCoverImage(existingProduct.getCoverImage());
            cartService.addToCart(session, addedItem);


            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }

    @PostMapping("/update-cart-item")
    @ResponseBody
    public ResponseEntity<String> updateCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(session, productId, quantity);
        return ResponseEntity.ok("Cart item updated.");
    }

    @GetMapping("/remove-cart-item/{id}")
    public String removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(session, id);
        return "redirect:/cart";
    }

    @GetMapping("/cart-item-count")
    @ResponseBody
    public int getCartItemCount() {
        return cartService.getCart(session).getCartItems().size();
    }

    @GetMapping("/checkout")
    public String getCheckOut(Model model) {
        CartDTO cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        double totalCart = cart.calculateTotalAmount();
        model.addAttribute("totalCart", totalCart);

        User curUser = getCurrentUser();
        OrderPerson orderPerson = new OrderPerson();
        orderPerson.setFullName(curUser.getFullName());
        orderPerson.setEmail(curUser.getEmail());
        orderPerson.setPhoneNumber(curUser.getPhoneNumber());
        orderPerson.setAddress(curUser.getAddress());
        model.addAttribute("orderPerson", orderPerson);

        return "user/checkout";
    }

    @GetMapping("/checkout-vnpay")
    public String getCheckOutVnPay(Model model) {
        CartDTO cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        double totalCart = cart.calculateTotalAmount();
        model.addAttribute("totalCart", totalCart);

        User curUser = getCurrentUser();
        OrderPerson orderPerson = new OrderPerson();
        orderPerson.setFullName(curUser.getFullName());
        orderPerson.setEmail(curUser.getEmail());
        orderPerson.setPhoneNumber(curUser.getPhoneNumber());
        orderPerson.setAddress(curUser.getAddress());
        model.addAttribute("orderPerson", orderPerson);

        return "user/checkout-vnpay";
    }

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute("orderPerson") OrderPerson orderPerson) {
        User curUser = getCurrentUser();
        orderService.createOrder(cartService.getCart(session), curUser, orderPerson);
        cartService.clearCart(session);
        return "redirect:/orders";
    }

//    @PostMapping("/place-order-vnpay")
//    public String placeOrderVnPay(@ModelAttribute("orderPerson") OrderPerson orderPerson){
//
//        return "user/checkout-vnpay";
//    }

}
