package com.google.cloud;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BindingsMap extends AbstractMap<Role, Set<Identity>> {
    private Set<Map.Entry<Role, Set<Identity>>> entrySet;

    static class BindingEntry implements Map.Entry<Role, Set<Identity>> {
        protected Binding binding;

        public BindingEntry(Binding binding) {
            this.binding = binding;
        }

        public Role getKey() {
            return this.binding.getRole();
        }

        public Set<Identity> getValue() {
            return this.binding.getIdentities();
        }

        public Set<Identity> setValue(Set<Identity> newValue) {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry e = (Map.Entry) o;
            Role key = this.binding.getRole();
            Set<Identity> value = this.binding.getIdentities();
            return (key == null ? e.getKey() == null : key.equals(e.getKey()))
                    && (value == null ? e.getValue() == null : value.equals(e
                    .getValue()));
        }

        public int hashCode() {
            return this.binding.hashCode();
        }
    }

    static class BindingsSet extends AbstractSet<Map.Entry<Role, Set<Identity>>> {
        protected List<Binding> bindings;

        static class BindingsSetIterator implements Iterator<Map.Entry<Role, Set<Identity>>> {
            protected Iterator<Binding> iterator;

            public BindingsSetIterator(List<Binding> bindings) {
                this.iterator = bindings.iterator();
            }

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public BindingEntry next() {
                return new BindingEntry(iterator.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public BindingsSet(List<Binding> bindings) {
            this.bindings = bindings;
        }

        public Iterator<Map.Entry<Role, Set<Identity>>> iterator() {
            return new BindingsSetIterator(bindings);
        }

        public int size() {
            return this.bindings.size();
        }
    }

    public BindingsMap(List<Binding> bindings) {
        if (entrySet == null) {
            entrySet = new BindingsSet(bindings);
        }
    }

    @Override
    public Set<Map.Entry<Role, Set<Identity>>> entrySet() {
        return entrySet;
    }
}
