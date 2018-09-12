package br.ufpe.cin.if688.parsing.analysis;

import java.util.*;

import br.ufpe.cin.if688.parsing.grammar.*;


public final class SetGenerator {

    public static Map<Nonterminal, Set<GeneralSymbol>> getFirst(Grammar g) {

        if (g == null) throw new NullPointerException("g nao pode ser nula.");

        Map<Nonterminal, Set<GeneralSymbol>> first = initializeNonterminalMapping(g);

        /*
         * Implemente aqui o método para retornar o conjunto first
         */

        Collection<Production> regras = g.getProductions();//lista das regras

        for(Production p : regras){//o for que percorre todas as regras
            HashSet<GeneralSymbol> auxFirst = new HashSet<GeneralSymbol>();//auxiliar que vai juntar os símbolos do first
            List<GeneralSymbol> regra = p.getProduction();//regra atual
            ArrayList recursao = new ArrayList();
            int indice = 1;//indice para recursao
            GeneralSymbol primeiroS = regra.get(0);
            Nonterminal nt = p.getNonterminal();
            recursao.add(nt);//coloca o nterminal na pilha
            recursao.add(primeiroS);//coloca o primeiro símbolo na pilha
            //até aqui ok
            //oi
            while(!(recursao.isEmpty() && indice>=recursao.size())){//isso vai rodar enquanto a "pilha" estiver vazia ou o indice não passou do tamanho da pilha
                if(primeiroS instanceof Nonterminal){//caso de ser nTerminal

                }
                else{//caso de ser EPISILON ou Terminal
                    if(first.get(nt).isEmpty()){//caso o set do first do nTerminal esteja vazio, podemos adicionar diretamente

                    }else{
                        auxFirst.addAll(first.get(nt));//pega tudo que já tem la em first do nterminal

                    }
                    auxFirst.add(primeiroS);//adiciona o nterminal atual
                    indice++;
                }
            }
            first.put(nt,auxFirst);
        }
        System.out.println(first);

        return first;

    }


    public static Map<Nonterminal, Set<GeneralSymbol>> getFollow(Grammar g, Map<Nonterminal, Set<GeneralSymbol>> first) {

        if (g == null || first == null)
            throw new NullPointerException();

        Map<Nonterminal, Set<GeneralSymbol>> follow = initializeNonterminalMapping(g);

        /*
         * implemente aqui o método para retornar o conjunto follow
         */

        return follow;
    }

    //método para inicializar mapeamento nãoterminais -> conjunto de símbolos
    private static Map<Nonterminal, Set<GeneralSymbol>>
    initializeNonterminalMapping(Grammar g) {
        Map<Nonterminal, Set<GeneralSymbol>> result =
                new HashMap<Nonterminal, Set<GeneralSymbol>>();

        for (Nonterminal nt: g.getNonterminals())
            result.put(nt, new HashSet<GeneralSymbol>());

        return result;
    }

}
