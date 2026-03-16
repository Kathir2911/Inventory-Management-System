package com.kathir.Inventory.Management.System.config;

import com.kathir.Inventory.Management.System.entity.Category;
import com.kathir.Inventory.Management.System.entity.Role;
import com.kathir.Inventory.Management.System.entity.User;
import com.kathir.Inventory.Management.System.repository.CategoryRepository;
import com.kathir.Inventory.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default users with different roles
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            
            User manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager123"))
                    .role(Role.ROLE_MANAGER)
                    .build();
            userRepository.save(manager);
            
            User staff = User.builder()
                    .username("staff")
                    .password(passwordEncoder.encode("staff123"))
                    .role(Role.ROLE_STAFF)
                    .build();
            userRepository.save(staff);
            
            System.out.println("✅ Default users created:");
            System.out.println("   - Admin: admin / admin123");
            System.out.println("   - Manager: manager / manager123");
            System.out.println("   - Staff: staff / staff123");
        }

        // Create default categories
        if (categoryRepository.count() == 0) {
            String[] categoryNames = {
                "Electronics", "Furniture", "Clothing", "Food & Beverages", 
                "Books", "Toys", "Sports", "Office Supplies"
            };
            
            String[] descriptions = {
                "Electronic devices and accessories",
                "Home and office furniture",
                "Apparel and fashion items",
                "Food products and drinks",
                "Books and publications",
                "Toys and games",
                "Sports equipment and gear",
                "Office and stationery supplies"
            };

            for (int i = 0; i < categoryNames.length; i++) {
                Category category = Category.builder()
                        .name(categoryNames[i])
                        .description(descriptions[i])
                        .build();
                categoryRepository.save(category);
            }
            System.out.println("✅ Default categories created (" + categoryNames.length + " categories)");
        }
    }
}
