package com.nexuswealth.customer.mapper;

import com.nexuswealth.customer.dto.request.CreateCustomerRequest;
import com.nexuswealth.customer.dto.request.UpdateCustomerRequest;
import com.nexuswealth.customer.dto.response.CustomerResponse;
import com.nexuswealth.customer.dto.response.CustomerSummaryResponse;
import com.nexuswealth.customer.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /*
     * Create request -> new Customer entity.
     *
     * These fields are controlled by the application/persistence layer
     * and must never come from the REST request.
     */
    @Mapping(target = "customerNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "kycVerified", ignore = true)
    Customer toEntity(CreateCustomerRequest request);

    /*
     * Customer entity -> detailed API response.
     */
    CustomerResponse toResponse(Customer customer);

    /*
     * Customer entity -> lightweight list response.
     *
     * fullName does not exist directly on Customer,
     * so we construct it from firstName + lastName.
     */
    @Mapping(
            target = "fullName",
            expression = "java(buildFullName(customer))"
    )
    CustomerSummaryResponse toSummaryResponse(Customer customer);

    /*
     * Update the existing entity instead of creating a new one.
     *
     * Server-managed fields remain untouched.
     */
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "kycVerified", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    void updateEntity(
            UpdateCustomerRequest request,
            @MappingTarget Customer customer
    );

    default String buildFullName(Customer customer) {

        if (customer == null) {
            return null;
        }

        String firstName =
                customer.getFirstName() == null
                        ? ""
                        : customer.getFirstName().trim();

        String lastName =
                customer.getLastName() == null
                        ? ""
                        : customer.getLastName().trim();

        return (firstName + " " + lastName).trim();
    }
}