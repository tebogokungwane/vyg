package com.vyg.loader;

import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import com.vyg.entity.Address;
import com.vyg.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressLoader implements CommandLineRunner {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) {
        if (addressRepository.count() == 0) {
            List<Address> addresses = List.of(

                    new  Address(Province.GAUTENG, Branch.ALEXANDRA, "698 Pretoria Main Road, CnrLink Road"),
                    new  Address(Province.GAUTENG, Branch.ALEXANDRA_2, "85 13th Avenue, Cnr Selbourne Street"),
                    new  Address(Province.GAUTENG, Branch.ORANGE_GROVE, "Cnr 5th Street & Louis Botha Avenue"),
                    new  Address(Province.GAUTENG, Branch.OLIEVEIN, "14 Serurubele Street, Ikati ave Oliven Ext 23"),
                    new  Address(Province.GAUTENG, Branch.BENONI, "70 Cranbourne Avenue (Next to ABSA)"),
                    new  Address(Province.GAUTENG, Branch.NIGEL, "Shop 1, Gazelle Street, Alra Park"),
                    new  Address(Province.GAUTENG, Branch.TSAKANE, "2744 Mabaso street"),
                    new  Address(Province.GAUTENG, Branch.DAVEYTON, "9397 Eislen Street (Next to Engen Garage)"),
                    new  Address(Province.GAUTENG, Branch.KWA_THEMA, "18053 Nkosi Street"),
                    new  Address(Province.GAUTENG, Branch.DOBSONVILLE, "913 Main Road"),
                    new  Address(Province.GAUTENG, Branch.WHITE_CITY, "797 Mputhi Street (Next to White Horse Hardware)"),
                    new  Address(Province.GAUTENG, Branch.ZOLA, "KwaMable Shops"),
                    new  Address(Province.GAUTENG, Branch.DIEPSLOOT, "Ext 4 (Opp. The Bridge)"),
                    new  Address(Province.GAUTENG, Branch.COSMO_CITY, "Shop GF1 Cosmo Junction, Cnr South Africa Dr and Tennessee Ave"),
                    new  Address(Province.GAUTENG, Branch.HILLBROW, "Highpoint, 60 Pretoria Street"),
                    new  Address(Province.GAUTENG, Branch.BRIXTON, "87 High Street (Next to Brixton Hyper pawn & car sales)"),
                    new  Address(Province.GAUTENG, Branch.YEOVILLE, "43 Raleigh Street (Opp Recreational Centre)"),
                    new  Address(Province.GAUTENG, Branch.NOORD_STREET, "20 Claim Street, Cnr Noord Street (Opp. BP Garage)"),
                    new  Address(Province.GAUTENG, Branch.BERTRAMS, "Shop No. 12, Spar Complex, Albertina Sisulu Street"),
                    new  Address(Province.GAUTENG, Branch.KAGISO, "Cnr Ngalonkulu Cres. & Thulandiwe Drive, Hillsview"),
                    new  Address(Province.GAUTENG, Branch.CARLETONVILLE, "4 Ada Street"),
                    new  Address(Province.GAUTENG, Branch.BEKKERSDAL, "1430 Kethlaetsi Street, Mandela Section"),
                    new  Address(Province.GAUTENG, Branch.FLORIDA, "1st floor Florida Plaza, 28 Goldman Street"),
                    new  Address(Province.GAUTENG, Branch.FOCHVILLE, "Mogijima Street, Kokosi Section"),
                    new  Address(Province.GAUTENG, Branch.KHUTSONG, "613 Xhosa Section (Next to White Horse Shop)"),
                    new  Address(Province.GAUTENG, Branch.LENASIA, "1 Station Place, Ext 1, Former Apsara Cinema"),
                    new  Address(Province.GAUTENG, Branch.MOHLAKENG, "2185 Nhlapo Street, Four Square"),
                    new  Address(Province.GAUTENG, Branch.LUIPAARDVLEI, "175 Main Reef Road"),
                    new  Address(Province.GAUTENG, Branch.RANDFONTEIN, "17 Sixth Street"),
                    new  Address(Province.GAUTENG, Branch.ROODEPOORT, "2 Mooi Street"),
                    new  Address(Province.GAUTENG, Branch.WESTONARIA, "Unit 2, 95 Briggs Street"),
                    new  Address(Province.GAUTENG, Branch.PRETORIA_CBD, "175 Nana Sita Street (Old Skinner Street), Pretoria Central"),
                    new  Address(Province.GAUTENG, Branch.MAMELODI, "No. 1 Dobolwane Street, Cnr Tsamaya Road, Mamelodi West (Next to Denneboom Station)"),
                    new  Address(Province.GAUTENG, Branch.MABOPANE, "2733 Block B (Next to Tlameolong Clinic)"),
                    new  Address(Province.GAUTENG, Branch.HAMMANSKRAAL, "4411 Unit 01 Temba, Kudube"),
                    new  Address(Province.GAUTENG, Branch.SOWETO, "Immink Dr, Diepkloof Zone 6, Soweto, 1864"),
                    new Address(Province.GAUTENG, Branch.PARK_STATION, "25 Plein Street, Johannesburg"),
                    new Address(Province.GAUTENG, Branch.ORLANDO_WEST, "9166 Sisulu Street, (Opp. Phomolong Train Station)"),
                    new Address(Province.GAUTENG, Branch.PIMVILLE, "5393 Myezane street, Zone 5 (Old BuyRite)"),
                    new Address(Province.GAUTENG, Branch.EMNDENI, "Moloi Shopping Centre, 579 Botani Street"),

                    new  Address(Province.VAAL_TRIANGLE, Branch.VANDERBIJLPARK, "Shop No. 4, C R Swart Street"),
                    new  Address(Province.VAAL_TRIANGLE, Branch.SEBOKENG, "392 Moshoeshoe Street, Zone 10"),
                    new  Address(Province.VAAL_TRIANGLE, Branch.VEREENIGING, "Shop 1, 8 Kruger Avenue (Opp. Sassa)"),
                    new  Address(Province.VAAL_TRIANGLE, Branch.REFENGKGOTSO, "41 Refengkgotso"),
                    new  Address(Province.VAAL_TRIANGLE, Branch.EVATON, "348 Bodea Road, (Next to Mmaila’s Shop)"),

                    new  Address(Province.WESTERN_CAPE, Branch.CAPE_TOWN, "7 Buitenkant Street (Near the Grand Parade)"),
                    new  Address(Province.WESTERN_CAPE, Branch.PHILLIPI, "427 Landsdowne Road (Next to Tesco)"),
                    new  Address(Province.WESTERN_CAPE, Branch.KHAYELITSHA, "54 Ntsikizi Street, Ilitha Park"),
                    new  Address(Province.WESTERN_CAPE, Branch.BELLVILLE, "16 Kruskal Avenue (Opp. KFC)"),
                    new  Address(Province.WESTERN_CAPE, Branch.MITCHELLS_PLAIN, "11 Polka Square, Town Centre"),
                    new  Address(Province.WESTERN_CAPE, Branch.CROSSROADS, "46 Stock Road (Opp. Goal Supermarket)"),
                    new  Address(Province.WESTERN_CAPE, Branch.NYANGA, "Nyang Junction"),

                    new  Address(Province.EASTERN_CAPE, Branch.EAST_LONDON, "5 Gladstone Street"),
                    new  Address(Province.EASTERN_CAPE, Branch.MDANTSANE, "9788 Qumza Highway Mdantsane Hotel"),
                    new  Address(Province.EASTERN_CAPE, Branch.QUEENSTOWN, "6 Bowker Street (Opp. Queenstown Police Station)"),
                    new  Address(Province.EASTERN_CAPE, Branch.KING_WILLIAMS_TOWN, "13 Market Street"),
                    new  Address(Province.EASTERN_CAPE, Branch.MTHATHA, "36 Owen Street"),
                    new  Address(Province.EASTERN_CAPE, Branch.PORT_ELIZABETH, "45 Norongo Road & Cnr Sopazi Street, New Brighton"),
                    new  Address(Province.EASTERN_CAPE, Branch.HUMANSORP, "64 Heugh Street, Cosmos Centre"),
                    new  Address(Province.EASTERN_CAPE, Branch.MOTHERWELL, "Weza Street, nu6"),
                    new  Address(Province.EASTERN_CAPE, Branch.NORONGO, "45 Norongo Road, Sopazi, New Brighton"),
                    new  Address(Province.EASTERN_CAPE, Branch.NJOLI, "Booi St, Zwide 3, Ibhayi, 6205"),
                    new  Address(Province.EASTERN_CAPE, Branch.GRAHAMSTOWN, "33 Beaufort Street"),

                    new  Address(Province.NORTHERN_CAPE, Branch.KIMBERLEY, "44 Sidney Street (Opp. The Library)"),
                    new  Address(Province.NORTHERN_CAPE, Branch.UPINGTON, "LeeuKop Avenue, Rosebudes Centrum, Rosedale"),

                    new  Address(Province.KWAZULU_NATAL, Branch.DURBAN, "465 Anton Lembede Street (Opp. Durodc Centre)"),
                    new  Address(Province.KWAZULU_NATAL, Branch.PINETOWN, "40 Kings Road, Above Chester butchery, Pinetown"),
                    new  Address(Province.KWAZULU_NATAL, Branch.KWAMASHU, "P50 Bhejane Road (Opp. Poly Clinic)"),
                    new  Address(Province.KWAZULU_NATAL, Branch.EMPANGENI, "7 Chrome Crescent Street"),
                    new  Address(Province.KWAZULU_NATAL, Branch.ISIPINGO, "122 Phila Ndwandwe Road, Isipingo Rail"),
                    new  Address(Province.KWAZULU_NATAL, Branch.NDNUINEFTON, "Complex Shop 45, 46, 47 Manenberg, 1st Floor Duinefontein (Nyanga Junction Shopping Centre)"),
                    new  Address(Province.KWAZULU_NATAL, Branch.PHOENIX, "Suit 9 - Redbro Centre, Parthenon Street, Phoenix Above Debonairs"),
                    new  Address(Province.KWAZULU_NATAL, Branch.PIETERMARITZBURG, "399 Church Street (Opp. Capital Centre)"),
                    new  Address(Province.KWAZULU_NATAL, Branch.NEWCASTLE, "Shop 13/14 Lot 627, Allen Street"),

                    new  Address(Province.FREE_STATE, Branch.BLOEMFONTEIN, "60 Oliver Tambo Road"),
                    new  Address(Province.FREE_STATE, Branch.THABA_NCHU, "16 Van Riebeeck Street"),
                    new  Address(Province.FREE_STATE, Branch.ALIWAL_NORTH, "63 Somerset Street"),
                    new  Address(Province.FREE_STATE, Branch.ROCKLANDS, "9374 Matheubula Street, Phelindaba"),
                    new  Address(Province.FREE_STATE, Branch.QWA_QWA, "Phuthaditjhaba: Midtown Shopping Complex, Shop No. 2"),
                    new  Address(Province.FREE_STATE, Branch.BETHLEHEM, "39 Maseko Street (Old location Post Office)"),
                    new  Address(Province.FREE_STATE, Branch.HARRISMITH, "47 Stuart Street (Opp. ABSA bank)"),
                    new  Address(Province.FREE_STATE, Branch.BOLATA, "Bolata Shopping Centre Shop No. 4"),

                    new  Address(Province.LIMPOPO, Branch.PIETERSBURG, "Shop 3, 20 Jorissen Street"),
                    new  Address(Province.MPUMALANGA, Branch.NELSPRUIT, "40 Bester Street (Next to Home Choice)"),
                    new  Address(Province.MPUMALANGA, Branch.WITBANK, "8 Arras Street, CBD"),
                    new  Address(Province.MPUMALANGA, Branch.MIDDELBURG, "2005 Xulu Street, Mountain View"),
                    new  Address(Province.MPUMALANGA, Branch.SECUNDA, "Embalenhle Ext 8, Stand 2258 Ingwe Drive"),

                    new  Address(Province.NORTH_WEST, Branch.RUSTENBURG, "134 Klopper Street"),
                    new  Address(Province.NORTH_WEST, Branch.JOUBERTON, "150 Emangweni Street, Close to 525, Ext 2"),
                    new  Address(Province.NORTH_WEST, Branch.POTCHEFSTROOM, "3680 Lekhele Street, Ikageng"),
                    new  Address(Province.NORTH_WEST, Branch.BRITS, "59 Pienaar Street, Shop 10"),
                    new  Address(Province.NORTH_WEST, Branch.MAHIKENG, "20-22 Martin Street (Old DR Church)")
            );

            addressRepository.saveAll(addresses);
            System.out.println("✔ Addresses seeded to the database.");
        } else {
            System.out.println("ℹ Addresses already exist, skipping seeding.");
        }
    }
}
