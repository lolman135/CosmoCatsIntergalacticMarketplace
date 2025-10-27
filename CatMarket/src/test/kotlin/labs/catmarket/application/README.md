# Some explanations

For testing usecase(service) layer, i've decided to use pure Mockito to mock repositories to 
test pure logic of use cases. After DB integration, IT will be responsible for testing full workflow of application,
that includes integration with db, stored in docker container