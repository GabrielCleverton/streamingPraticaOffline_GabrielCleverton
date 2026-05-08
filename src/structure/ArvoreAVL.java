package structure;

import model.Filme;

public class ArvoreAVL {
    NoAVL raiz = null;

    void inserir(Filme filme){
        raiz = inserir(raiz, filme);
    }
    NoAVL inserir(NoAVL arv, Filme filme){
        if (arv == null){
            return new NoAVL(filme);
        }
        else if (filme.getId() < arv.getFilme().getId()){
            arv.setEsq(inserir(arv.getEsq(), filme));
        }else if (filme.getId() > arv.getFilme().getId()){
            arv.setDir(inserir(arv.getDir(), filme));
        }else{
            return arv;
        }

        arv.setAltura(1+maior(altura(arv.getEsq()), altura(arv.getDir())));

        int fb = obterFB(arv);
        int fbSubArvEsq = obterFB(arv.getEsq());
        int fbSubArvDir = obterFB(arv.getDir());

        if (fb > 1 && fbSubArvEsq >= 0){
            return rotacaoDireitaSimples(arv);
        }

        if (fb < -1 && fbSubArvDir <= 0){
            return rotacaoEsquerdaSimples(arv);
        }

        if (fb > 1 && fbSubArvEsq < 0){
            arv.setEsq(rotacaoEsquerdaSimples(arv.getEsq()));
            return rotacaoDireitaSimples(arv);
        }

        if (fb < -1 && fbSubArvDir > 0){
            arv.setDir(rotacaoDireitaSimples(arv.getDir()));
            return rotacaoEsquerdaSimples(arv);
        }

        return arv;
    }
    void remover(Filme filme){
        raiz = remover(raiz, filme);
    }
    NoAVL remover(NoAVL arv, Filme filme){
        if (arv == null){
            return arv;
        }
        if (filme.getId() < arv.getFilme().getId()){
            arv.setEsq(remover(arv.getEsq(),filme));
        }
        else if (filme.getId() > arv.getFilme().getId()) {
            arv.setDir(remover(arv.getDir(), filme));
        }else {
            if (arv.getEsq() == null && arv.getDir() == null){
                arv = null;
            } else if (arv.getEsq() == null) {
                NoAVL temp = arv;
                arv = temp.getDir();
                temp = null;
            } else if (arv.getDir() == null) {
                NoAVL temp = arv;
                arv = temp.getEsq();
                temp = null;
            }else {
                NoAVL temp = menorChave(arv.getDir());

                arv.setFilme(temp.getFilme());

                arv.setDir(remover(arv.getDir(), temp.getFilme()));
            }
        }
        if (arv == null){
            return arv;
        }
        arv.setAltura(1 + maior(altura(arv.getEsq()), altura(arv.getDir())));
        int fb = obterFB(arv);
        int fbSubArvEsq = obterFB(arv.getEsq());
        int fbSubArvDir = obterFB(arv.getDir());

        if (fb > 1 && fbSubArvEsq >= 0){
            return rotacaoDireitaSimples(arv);
        }
        if (fb < -1 && fbSubArvDir <= 0){
            return rotacaoEsquerdaSimples(arv);
        }
        if (fb > 1 && fbSubArvEsq < 0){
            arv.setEsq(rotacaoEsquerdaSimples(arv.getEsq()));
            return rotacaoDireitaSimples(arv);
        }
        if (fb < -1 && fbSubArvDir > 0){
            arv.setDir(rotacaoDireitaSimples(arv.getDir()));
            return rotacaoEsquerdaSimples(arv);
        }
        return arv;
    }
    int altura(NoAVL arv){
        if (arv == null){
            return -1;
        }
        return arv.getAltura();
    }
    int maior(int a, int b){
        return (a>b) ? a:b;
    }
    int obterFB(NoAVL arv){
        if(arv==null){
            return 0;
        }
        return altura(arv.getEsq()) - altura(arv.getDir());
    }
    NoAVL menorChave(NoAVL arv){
        NoAVL temp = arv;
        if (temp == null) {
            return null;
        }

        while (temp.getEsq() != null){
            temp = temp.getEsq();
        }
        return temp;
    }

    NoAVL rotacaoDireitaSimples(NoAVL y){
        NoAVL x = y.getEsq();
        NoAVL z = x.getDir();

        x.setDir(y);
        y.setEsq(z);

        y.setAltura(maior(altura(y.getEsq()), altura(y.getDir()))+1);
        x.setAltura(maior(altura(x.getEsq()), altura(x.getDir()))+1);

        return x;
    }

    NoAVL rotacaoEsquerdaSimples(NoAVL x){
        NoAVL y = x.getDir();
        NoAVL z = y.getEsq();

        y.setEsq(x);
        x.setDir(z);

        x.setAltura(maior(altura(x.getEsq()), altura(x.getDir()))+1);
        y.setAltura(maior(altura(y.getEsq()), altura(y.getDir()))+1);

        return y;
    }

    Filme buscar(int id){
        return buscar(raiz, id);
    }

    Filme buscar(NoAVL arv, int id){
        if (arv == null){
            return null;
        }
        if (id < arv.getFilme().getId()){
            return buscar(arv.getEsq(), id);
        } else if (id > arv.getFilme().getId()) {
            return buscar(arv.getDir(), id);
        }else {
            arv.setUltimoAcesso(arv.getUltimoAcesso() + 1);
            return arv.getFilme();
        }
    }

    void evictLRU(){
        if (raiz == null) return;
        NoAVL menor = encontrarMenorAcesso(raiz);
        remover(menor.getFilme());
    }

    NoAVL encontrarMenorAcesso(NoAVL arv){
        if (arv == null){
            return null;
        }

        NoAVL menor = arv;
        NoAVL candidatoEsq = encontrarMenorAcesso(arv.getEsq());
        NoAVL candidatoDir = encontrarMenorAcesso(arv.getDir());

        if (candidatoEsq != null && candidatoEsq.getUltimoAcesso() < menor.getUltimoAcesso()){
            menor = candidatoEsq;
        }

        if (candidatoDir != null && candidatoDir.getUltimoAcesso() < menor.getUltimoAcesso()){
            menor = candidatoDir;
        }

        return menor;
    }
}
