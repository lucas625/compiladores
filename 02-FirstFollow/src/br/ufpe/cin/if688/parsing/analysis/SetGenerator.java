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
        ArrayList<Integer> indices = new ArrayList<Integer>();
        boolean houveM = true;
        int a =0;
        for(Production p : regras){//inicializando a lista de indices
            indices.add(0);
            a++;
        }
        while(houveM){
            houveM = false;
            int indiceR = 0;
            for(Production p : regras){//o for que percorre todas as regras
                HashSet<GeneralSymbol> auxFirst = new HashSet<GeneralSymbol>();//auxiliar que vai juntar os símbolos do first
                HashSet<Nonterminal> temVazio = new HashSet<Nonterminal>();
                List<GeneralSymbol> regra = p.getProduction();//regra atual

                int indice = indices.get(indiceR);//indice atual
                GeneralSymbol primeiroS = regra.get(indice);
                Nonterminal nt = p.getNonterminal();
                int tamanho = regra.size();
                //até aqui ok
                if(primeiroS instanceof Nonterminal ){//caso de ser nTerminal
                    for(GeneralSymbol s : first.get(primeiroS)){
                        if(!(s instanceof SpecialSymbol))
                            auxFirst.add(s);
                    }
                    if(first.get(primeiroS).contains(SpecialSymbol.EPSILON)) {
                        try {
                            if (regra.get(indice + 1) == null) {
                                indices.set(indiceR,indice+1);//ou seja o nTerminal pode ser vazio 
                            }
                        } catch (Exception e) {

                            auxFirst.add(SpecialSymbol.EPSILON);
                        }
                    }
                    if(!first.get(nt).containsAll(auxFirst)) {
                        auxFirst.addAll(first.get(nt));
                        houveM = true;
                    }
                }
                else if(primeiroS instanceof Terminal){//caso de ser Terminal
                    if(!first.get(nt).contains(primeiroS)) {
                        auxFirst.addAll(first.get(nt));//pega tudo que já tem la em first do nterminal
                        auxFirst.add(primeiroS);//adiciona o nterminal atual
                        houveM = true;
                    }
                }
                else if(primeiroS instanceof SpecialSymbol){//caso de ser EPISILOM
                    boolean vazio = false;
                    for(int i=0;i<tamanho && !vazio;i++){
                        GeneralSymbol atual = regra.get(i);
                        if(!(atual instanceof SpecialSymbol)) {
                            vazio = true;
                            if(atual instanceof Terminal){//caso por exemplo de B-> &b
                                if(!first.get(nt).contains(atual)) {
                                    auxFirst.addAll(first.get(nt));
                                    auxFirst.add(atual);
                                    houveM = true;
                                }
                            }else if(atual instanceof Nonterminal){//caso B->&
                                if(!first.get(nt).containsAll(first.get(atual))) {
                                    auxFirst.addAll(first.get(nt));
                                    auxFirst.addAll(first.get(primeiroS));
                                    houveM = true;
                                }
                            }
                        }
                    }
                    if(!vazio && !first.get(nt).contains(SpecialSymbol.EPSILON)){//esse seria o caso de que só tem epsilom na regra.
                        auxFirst.addAll(first.get(nt));
                        auxFirst.add(primeiroS);//adicionando o símbolo vazio
                        temVazio.add(nt);
                        houveM = true;
                    }
                }else{
                    System.out.println("regra inválida");
                }
                if(houveM)
                    first.put(nt, auxFirst);
                   // System.out.println("entrou")
                //System.out.println("chegou aki");
                indiceR++;
            }
        }
        //aqui já colocamos todos os terminais e todos os simbolos epsilom no first, agora falta
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
