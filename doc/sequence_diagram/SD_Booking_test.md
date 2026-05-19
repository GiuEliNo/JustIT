@startuml

participant User <<Actor>>
participant SearchListShopGController

User -> SearchListShopGController: onSelectTab()
activate SearchListShopGController

SearchListShopGController -> BrowseShopController **: new BrowseShopController



SearchListShopGController -> BrowseShopController : getAllShops()
activate BrowseShopController
BrowseShopController -> ShopDAOJDBC : retrieveAllShops()
activate ShopDAOJDBC
return
return
return


User -> SearchListShopGController :onSelectPage()
activate SearchListShopGController
SearchListShopGController -> BrowseShopController :pageSelected(ShopBean bean)
activate BrowseShopController
BrowseShopController -> Shop : <<create>>
activate Shop
return
BrowseShopController -> SessionManager :setCurrentShop(Shop selectedItem)
activate SessionManager
return
return
SearchListShopGController ->> PageShopUserGController **: new PageShopUserGController
return


activate PageShopUserGController
PageShopUserGController -> ShopController : <<create>>
activate ShopController
ShopController -> SessionManager : getShopBean()
activate SessionManager
return
return

User ->  PageShopUserGController :onBookingClicked()
activate PageShopUserGController
PageShopUserGController -> BookingPageGController : <<create>>
activate BookingPageGController






@enduml
