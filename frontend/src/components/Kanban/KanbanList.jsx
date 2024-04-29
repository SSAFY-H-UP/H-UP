import React from 'react';
import KanbanCreator from './KanbanCreator';
import { useDrop } from 'react-dnd';
import styles from './KanbanList.module.scss';

function KanbanList({ title, children }) {
  const [{ canDrop, isOver }, drop] = useDrop({
    accept: 'card',
    drop: () => ({ name: title }),
    collect: (monitor) => ({
      isOver: monitor.isOver(),
      canDrop: monitor.canDrop(),
    }),
  });

  return (
    <div className={styles.container}>
      <div className="kanbanListWrap" ref={drop}>
        <div className="kanbanTitle">{title}</div>
        {children}
        <KanbanCreator title={title} />
      </div>
    </div>
  );
}

export default React.memo(KanbanList);