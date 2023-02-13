package com.android.cargolink.entities

/**
 *
 * data class to store the shipment entity information, it has been
 * decided to create a class for future growth and better data definition
 *
 * @constructor creates a shipment instance with the address of the shipment,
 * made of "house" number, street name and interior address all separated by
 * a "space" character (" "). The interior address is composed by a short
 * descriptor and a interior number separated by an space.
 * example: "HouseNumber StreetName InteriorDesc
 * AptNumber" - "123 Street Apt 456"
 *
 * Assumptions: Address format described above.
 * For exercise calculations we will take only the portion of Street name
 * described above as "Street Name"
 * For the moment we will only include the example "short descriptions" for
 * the address processing, these being Apt. and Suite
 *
 */
data class Shipment(val address: String) {

    private val SHORT_DESCRIPTORS: Set<String> = setOf("Apt.", "Suite")

    val streetName: String by lazy {
        exteriorAddress.substringAfter(' ')
    }

    private val exteriorAddress: String by lazy {
        var temp = address
        SHORT_DESCRIPTORS.forEach { desc ->
            if(address.contains(desc)) {
                temp = address.substringBefore(desc).trim()
                return@forEach //Used return as an ugly solution to avoid extra code from while
            }
        }
        temp
    }
}
