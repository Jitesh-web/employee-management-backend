package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.AddressDto;
import net.javaguides.ems.entity.Address;

public class AddressMapper {
    public static AddressDto mapToAddressDto(Address address) {

        if (address == null) {
            return null;
        }

        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());

        return addressDto;
    }

    public static Address mapToAddress(AddressDto addressDto) {

        if (addressDto == null) {
            return null;
        }

        Address address = new Address();
        address.setId(addressDto.getId());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());

        return address;
    }
}
