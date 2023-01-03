# *PAQ SAE Affectation*

![paq](https://yt3.ggpht.com/a/AATXAJyYi2YlBdjLeQMOg2EBkWK4KyeWZjcPRYhr0A=s900-c-k-c0xffffffff-no-rj-mo)

## 1. Langues

| Critères | Choix |
|----------|-------|
| **Langue de codage** | Anglais |
|**Langue de l'utilisateur** | Français |
|**Langue de la JavaDoc** | Français |

## 2. Forme de la JavaDoc

    Tout ce qui est décris dans la classe "Class" suivante est à suivre à la lettre
    (Ordre des sections, nom des sections, manières d'écrire). Seul le contenu des 
    commentaires et méthodes peut varier selon les cas.

```java
/**
/* <Description de la classe>
/* @author <auteur>
/*
**/

public class Class {

    // Attributs de classe
    private int attributInt;
    private String attributString;

    // Mutateurs && Accesseurs
    /**
    /* <Description>
    /* @return <Description>
    **/
    public int getAttributInt() {
        return this.attributInt;
    }

    /**
    /* <Description>
    /* @param attributInt <Description>
    **/
    public void setAttributInt(int attributInt) {
        this.attributInt = attributInt;
    }

    // Contructeurs
    /**
    /* <Description du constrcuteur>
    /* @param attributInt <Description>
    /* @param attributString <Description>
    **/
    public Class(int attributInt, String attributString) {
        this.attributInt = attributInt;
        this.attributString = attributString;
    }

    // Méthodes
    /**
    /* <Description de la méthode>
    /* @param toAdd <Description>
    /* @return <Description>
    **/
    public int method1(int toAdd) {
        return this.attributInt += toAdd;
    }

    // toString && HashCode && Equals
    @Override
    public String toString() {
        return "Class [attributInt=" + this.attributInt + ", attributString=" + 
        attributString + "]";
    }


}
