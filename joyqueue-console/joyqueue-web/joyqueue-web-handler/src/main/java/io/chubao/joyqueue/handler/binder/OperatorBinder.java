package io.chubao.joyqueue.handler.binder;


import io.chubao.joyqueue.handler.annotation.Operator;
import io.chubao.joyqueue.model.domain.Application;
import io.chubao.joyqueue.model.domain.Identity;
import io.chubao.joyqueue.model.domain.User;
import com.jd.laf.binding.binder.Binder;
import com.jd.laf.binding.reflect.exception.ReflectionException;
import io.vertx.ext.web.RoutingContext;

import static io.chubao.joyqueue.handler.Constants.APPLICATION;
import static io.chubao.joyqueue.handler.Constants.USER_KEY;

/**
 * operator binder
 * Created by chenyanying3 on 19-3-13.
 */
public class OperatorBinder implements Binder {

    @Override
    public boolean bind(final Context context) throws ReflectionException {
        if (context == null) {
            return false;
        }
        Object obj = context.getSource();
        if (!(obj instanceof RoutingContext)) {
            return false;
        }
        RoutingContext ctx = (RoutingContext) obj;
        Application application = ctx.get(APPLICATION);
        User session = ctx.get(USER_KEY);
        Identity identity = session != null ? new Identity(session) : (application != null ? application.getOwner() : null);
        return context.bind(identity);
    }

    @Override
    public Class<?> annotation() {
        return Operator.class;
    }
}
