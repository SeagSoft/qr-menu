package com.qrmenu.platform.common.config;

import com.qrmenu.platform.auth.entity.StaffUser;
import com.qrmenu.platform.auth.model.Role;
import com.qrmenu.platform.auth.repository.StaffUserRepository;
import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.menu.entity.*;
import com.qrmenu.platform.menu.repository.MenuCategoryRepository;
import com.qrmenu.platform.menu.repository.ProductRepository;
import com.qrmenu.platform.ordering.entity.CustomerOrder;
import com.qrmenu.platform.ordering.entity.OrderItem;
import com.qrmenu.platform.ordering.entity.OrderStatus;
import com.qrmenu.platform.ordering.repository.CustomerOrderRepository;
import com.qrmenu.platform.table.entity.RestaurantTable;
import com.qrmenu.platform.table.repository.RestaurantTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DataInitializer.class);
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final StaffUserRepository staffUserRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerOrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BusinessRepository businessRepository, BranchRepository branchRepository, StaffUserRepository staffUserRepository, RestaurantTableRepository tableRepository, MenuCategoryRepository categoryRepository, ProductRepository productRepository, CustomerOrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.businessRepository = businessRepository;
        this.branchRepository = branchRepository;
        this.staffUserRepository = staffUserRepository;
        this.tableRepository = tableRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (businessRepository.count() > 0) {
            Business existingBusiness = businessRepository.findAll().get(0);
            Branch existingBranch = branchRepository.findAll().stream().findFirst().orElseGet(() -> {
                Branch b = Branch.builder().business(existingBusiness).name("Main Branch").address("123 Culinary Boulevard, Downtown").phone("+1 (555) 019-2832").isActive(true).build();
                return branchRepository.save(b);
            });
            if (staffUserRepository.count() == 0) {
                String encodedPwd = passwordEncoder.encode("password123");
                staffUserRepository.save(StaffUser.builder().business(existingBusiness).branch(existingBranch).email("admin@gourmet.com").passwordHash(encodedPwd).firstName("Admin").lastName("Manager").role(Role.ADMIN).isActive(true).build());
                staffUserRepository.save(StaffUser.builder().business(existingBusiness).branch(existingBranch).email("waiter@gourmet.com").passwordHash(encodedPwd).firstName("John").lastName("Waiter").role(Role.WAITER).isActive(true).build());
                staffUserRepository.save(StaffUser.builder().business(existingBusiness).branch(existingBranch).email("cashier@gourmet.com").passwordHash(encodedPwd).firstName("Sarah").lastName("Cashier").role(Role.CASHIER).isActive(true).build());
                staffUserRepository.save(StaffUser.builder().business(existingBusiness).branch(existingBranch).email("kitchen@gourmet.com").passwordHash(encodedPwd).firstName("Chef").lastName("Gordon").role(Role.KITCHEN).isActive(true).build());
            }
            if (tableRepository.count() == 0) {
                tableRepository.save(RestaurantTable.builder().business(existingBusiness).branch(existingBranch).tableNumber("01").qrToken("tbl-001").isActive(true).build());
            }
            if (categoryRepository.count() == 0) {
                MenuCategory cat = categoryRepository.save(MenuCategory.builder().business(existingBusiness).name("Burgers").description("Handcrafted gourmet beef & chicken burgers").sortOrder(1).isActive(true).build());
                productRepository.save(Product.builder().category(cat).name("Truffle Beef Burger").description("Black truffle mayo, aged cheddar, caramelized onions, brioche bun").price(new BigDecimal("18.99")).imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80").sortOrder(1).calories(780).prepTimeMinutes(15).tags(List.of("Chef Special", "Bestseller")).isActive(true).build());
            }
            log.info("Database already contains data (Business ID: {}, Branch ID: {}), ensured required seed records exist.", existingBusiness.getId(), existingBranch.getId());
            return;
        }
        log.info("Seeding initial database data...");
        // 1. Create Business
        Business business = Business.builder().name("Gourmet Restaurant").slug("gourmet").ownerEmail("owner@gourmet.com").phone("+1 (555) 019-2831").isActive(true).build();
        business = businessRepository.save(business);
        // 2. Create Branch
        Branch branch = Branch.builder().business(business).name("Main Branch").address("123 Culinary Boulevard, Downtown").phone("+1 (555) 019-2832").isActive(true).build();
        branch = branchRepository.save(branch);
        // 3. Create Staff Users
        String encodedPassword = passwordEncoder.encode("password123");
        StaffUser adminUser = StaffUser.builder().business(business).branch(branch).email("admin@gourmet.com").passwordHash(encodedPassword).firstName("Admin").lastName("Manager").role(Role.ADMIN).isActive(true).build();
        staffUserRepository.save(adminUser);
        StaffUser waiterUser = StaffUser.builder().business(business).branch(branch).email("waiter@gourmet.com").passwordHash(encodedPassword).firstName("John").lastName("Waiter").role(Role.WAITER).isActive(true).build();
        staffUserRepository.save(waiterUser);
        StaffUser cashierUser = StaffUser.builder().business(business).branch(branch).email("cashier@gourmet.com").passwordHash(encodedPassword).firstName("Sarah").lastName("Cashier").role(Role.CASHIER).isActive(true).build();
        staffUserRepository.save(cashierUser);
        StaffUser kitchenUser = StaffUser.builder().business(business).branch(branch).email("kitchen@gourmet.com").passwordHash(encodedPassword).firstName("Chef").lastName("Gordon").role(Role.KITCHEN).isActive(true).build();
        staffUserRepository.save(kitchenUser);
        // 4. Create Tables (including demo token tbl-001)
        RestaurantTable table1 = RestaurantTable.builder().business(business).branch(branch).tableNumber("01").qrToken("tbl-001").isActive(true).build();
        tableRepository.save(table1);
        RestaurantTable table2 = RestaurantTable.builder().business(business).branch(branch).tableNumber("02").qrToken("tbl-002").isActive(true).build();
        tableRepository.save(table2);
        RestaurantTable table3 = RestaurantTable.builder().business(business).branch(branch).tableNumber("03").qrToken("tbl-003").isActive(true).build();
        tableRepository.save(table3);
        // 5. Create Menu Categories & Products
        // Category 1: Burgers
        MenuCategory burgersCat = MenuCategory.builder().business(business).name("Burgers").description("Handcrafted gourmet beef & chicken burgers served on toasted brioche").sortOrder(1).isActive(true).build();
        burgersCat = categoryRepository.save(burgersCat);
        Product burger1 = Product.builder().category(burgersCat).name("Truffle Beef Burger").description("Black truffle mayo, aged cheddar, caramelized onions, organic arugula, brioche bun").price(new BigDecimal("18.99")).imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80").sortOrder(1).calories(780).prepTimeMinutes(15).tags(List.of("Chef Special", "Bestseller")).isActive(true).variants(new ArrayList<>()).optionGroups(new ArrayList<>()).build();
        ProductVariant burgerVariant1 = ProductVariant.builder().product(burger1).name("Single Patty").price(new BigDecimal("18.99")).build();
        ProductVariant burgerVariant2 = ProductVariant.builder().product(burger1).name("Double Patty").price(new BigDecimal("23.99")).build();
        burger1.getVariants().add(burgerVariant1);
        burger1.getVariants().add(burgerVariant2);
        OptionGroup toppingGroup = OptionGroup.builder().product(burger1).name("Extra Toppings").minSelect(0).maxSelect(3).options(new ArrayList<>()).build();
        ProductOption opt1 = ProductOption.builder().optionGroup(toppingGroup).name("Extra Cheddar Cheese").priceModifier(new BigDecimal("2.00")).build();
        ProductOption opt2 = ProductOption.builder().optionGroup(toppingGroup).name("Crispy Bacon").priceModifier(new BigDecimal("3.00")).build();
        toppingGroup.getOptions().add(opt1);
        toppingGroup.getOptions().add(opt2);
        burger1.getOptionGroups().add(toppingGroup);
        productRepository.save(burger1);
        Product burger2 = Product.builder().category(burgersCat).name("Smokey Bacon Cheese Burger").description("Smoked bacon, sharp cheddar, house BBQ sauce, crispy lettuce & tomato").price(new BigDecimal("16.50")).imageUrl("https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=800&q=80").sortOrder(2).calories(720).prepTimeMinutes(12).tags(List.of("Popular")).isActive(true).build();
        productRepository.save(burger2);
        // Category 2: Pizzas
        MenuCategory pizzasCat = MenuCategory.builder().business(business).name("Pizzas").description("Wood-fired Neapolitan sourdough pizzas baked at 900 degrees").sortOrder(2).isActive(true).build();
        pizzasCat = categoryRepository.save(pizzasCat);
        Product pizza1 = Product.builder().category(pizzasCat).name("Artisan Margherita Pizza").description("San Marzano tomatoes, fresh buffalo mozzarella, organic basil, extra virgin olive oil").price(new BigDecimal("15.99")).imageUrl("https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?auto=format&fit=crop&w=800&q=80").sortOrder(1).calories(650).prepTimeMinutes(14).tags(List.of("Vegetarian")).isActive(true).variants(new ArrayList<>()).build();
        ProductVariant pizzaVariant1 = ProductVariant.builder().product(pizza1).name("Medium 12\"").price(new BigDecimal("15.99")).build();
        ProductVariant pizzaVariant2 = ProductVariant.builder().product(pizza1).name("Large 16\"").price(new BigDecimal("20.99")).build();
        pizza1.getVariants().add(pizzaVariant1);
        pizza1.getVariants().add(pizzaVariant2);
        productRepository.save(pizza1);
        // Category 3: Drinks
        MenuCategory drinksCat = MenuCategory.builder().business(business).name("Drinks").description("Fresh juices, craft lemonades & house mocktails").sortOrder(3).isActive(true).build();
        drinksCat = categoryRepository.save(drinksCat);
        Product drink1 = Product.builder().category(drinksCat).name("Craft Passionfruit Lemonade").description("Fresh passionfruit juice, sparkling soda water, organic mint leaves & cane syrup").price(new BigDecimal("5.99")).imageUrl("https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=800&q=80").sortOrder(1).calories(120).prepTimeMinutes(5).tags(List.of("Refreshing")).isActive(true).build();
        productRepository.save(drink1);
        // Category 4: Desserts
        MenuCategory dessertsCat = MenuCategory.builder().business(business).name("Desserts").description("Indulgent sweet treats crafted by our pastry chef").sortOrder(4).isActive(true).build();
        dessertsCat = categoryRepository.save(dessertsCat);
        Product dessert1 = Product.builder().category(dessertsCat).name("Lava Chocolate Cake").description("Warm Belgian chocolate cake with molten fudge center & Madagascar vanilla gelato").price(new BigDecimal("8.99")).imageUrl("https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=800&q=80").sortOrder(1).calories(540).prepTimeMinutes(8).tags(List.of("Sweet")).isActive(true).build();
        productRepository.save(dessert1);
        // 6. Seed Sample Orders for Kitchen & Sales Reports
        if (orderRepository.count() == 0) {
            CustomerOrder sampleOrder1 = CustomerOrder.builder().branch(branch).table(table1).orderNumber("ORD-1001").status(OrderStatus.SERVED).paymentMethod("CARD").tipAmount(new BigDecimal("4.40")).totalAmount(new BigDecimal("48.37")).notes("Paid via Credit Card (Tip: 10%)").build();
            sampleOrder1.addItem(OrderItem.builder().productName("Truffle Beef Burger").quantity(2).unitPrice(new BigDecimal("18.99")).totalPrice(new BigDecimal("37.98")).selectedVariantName("Single Patty").build());
            sampleOrder1.addItem(OrderItem.builder().productName("Craft Passionfruit Lemonade").quantity(1).unitPrice(new BigDecimal("5.99")).totalPrice(new BigDecimal("5.99")).build());
            orderRepository.save(sampleOrder1);
            CustomerOrder sampleOrder2 = CustomerOrder.builder().branch(branch).table(table2).orderNumber("ORD-1002").status(OrderStatus.SERVED).paymentMethod("COUNTER").tipAmount(new BigDecimal("2.50")).totalAmount(new BigDecimal("27.48")).notes("Paid via Cash at Counter (Tip: 10%)").build();
            sampleOrder2.addItem(OrderItem.builder().productName("Artisan Margherita Pizza").quantity(1).unitPrice(new BigDecimal("15.99")).totalPrice(new BigDecimal("15.99")).selectedVariantName("Medium 12\"").build());
            sampleOrder2.addItem(OrderItem.builder().productName("Lava Chocolate Cake").quantity(1).unitPrice(new BigDecimal("8.99")).totalPrice(new BigDecimal("8.99")).build());
            orderRepository.save(sampleOrder2);
        }
        log.info("Initial data successfully seeded into database! (Business ID: {}, Branch ID: {}, Table token: tbl-001)", business.getId(), branch.getId());
    }
}
