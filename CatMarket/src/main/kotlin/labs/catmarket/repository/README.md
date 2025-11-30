# Hello

1.  Little explanations (I know that I did too much, but I want to explain my ideas).<br>
    I've decided to use natural id repositories because they work with business ids, or default ids in domain (uuid).
    So, if natural id repository works with natural (business) id, I'll use it as implementations of my repositories. 
2.  I don't want to rewrite use cases, so i'm going to use a wrap repositories, or domain implementations. They will
    wrap standard jpa-extended repositories and mappers, that map jpa entities to domains 